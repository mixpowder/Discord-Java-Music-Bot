package mixpowder.jmusicbot.cores;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Cores{
	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;
	private GuildMusicManager musicManager;
	private  AudioManager audioManager;
	private config config;

	public Cores(config config){
		this.musicManagers = new HashMap<>();
		this.config = config;
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

	public void loadAndPlay(final TextChannel channel, final String trackUrl) {
		musicManager = getGuildAudioPlayer(channel.getGuild());
		audioManager = channel.getGuild().getAudioManager();
		musicManager.player().setVolume(Integer.parseInt(config.getSettings("volume")));
	    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
	    	@Override
	    	public void trackLoaded(AudioTrack track) {
	    		channel.sendMessage(track.getInfo().title + "が再生リストに追加されました").queue();
	    		play(channel.getGuild(), musicManager, track);
	    	}

	    	public void playlistLoaded(AudioPlaylist playlist) {
	    		for(AudioTrack track : playlist.getTracks()){
	    			play(channel.getGuild(), musicManager, track);
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

	  	public void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
	  		//this.audioManager = guild.getAudioManager();
	  		connectToFirstVoiceChannel(guild.getAudioManager());
	  		musicManager.scheduler().queue(track);
	  	}

	  	public void skipTrack() {
	  		musicManager.scheduler().nextTrack();
	  	}


		private static void connectToFirstVoiceChannel(AudioManager audioManager) {
	  		if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
	  			for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
	  				if(voiceChannel.getMembers().size() == 0){
	  					continue;
	  				}else{
	  					audioManager.openAudioConnection(voiceChannel);
	  					break;
	  				}
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