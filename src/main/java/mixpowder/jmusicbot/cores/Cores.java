package mixpowder.jmusicbot.cores;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Cores{
	private final AudioPlayerManager playerManager;
	private GuildMusicManager musicManager;
	private  AudioManager audioManager;
	private config config;

	public Cores(config config){
		this.config = config;
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
		this.musicManager = new GuildMusicManager(playerManager);
	}

	public void loadAndPlay(final TextChannel channel,final Member member, final String trackUrl) {
		channel.getGuild().getAudioManager().setSendingHandler(musicManager.getSendHandler());
		audioManager = channel.getGuild().getAudioManager();
		musicManager.player().setVolume(Integer.parseInt(config.getSettings("volume")));
	    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
	    	@Override
	    	public void trackLoaded(AudioTrack track) {
	    		channel.sendMessage(track.getInfo().title + "が再生リストに追加されました").queue();
	    		play(channel, track, member);
	    	}

	    	public void playlistLoaded(AudioPlaylist playlist) {
	    		for(AudioTrack track : playlist.getTracks()){
	    			play(channel, track, member);
	    		}
	    		channel.sendMessage(playlist.getTracks().size() + "曲が再生リストに追加されました").queue();

	    	}

	    	public void noMatches() {
	    	  channel.sendMessage(trackUrl + "は存在しないURLです").queue();
	      	}

	      	public void loadFailed(FriendlyException exception) {
	      	}
	    });
	}

	  	public void play(TextChannel channel, AudioTrack track, Member member) {
	  		connectToFirstVoiceChannel(member);
	  		musicManager.scheduler().queue(track,channel);
	  	}

	  	public void skipTrack() {
	  		musicManager.scheduler().nextTrack();
	  	}


	  	private void connectToFirstVoiceChannel(Member member) {
	  		if (!audioManager.isConnected()) {
	  			if(member.getVoiceState().getChannel() == null){
	  				for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
	  					if(voiceChannel.getMembers().size() == 0){
	  						continue;
	  					}else{
	  						audioManager.openAudioConnection(voiceChannel);
	  						break;
	  					}
	  				}
	  			}else{
	  				audioManager.openAudioConnection(member.getVoiceState().getChannel());
	  			}
	  		}
	  	}

	  	public AudioManager audioManager(){
	  		return audioManager;
	  	}

	  	public GuildMusicManager musicManager(){
	  		return musicManager;
	  	}

	  	public config config(){
	  		return config;
	  	}

	  	public String node(String data){
	  		return config.getSettings(data);
	  	}

	  	public void setvolume(String data){
	  		config.setvolume(data);
	  	}
}