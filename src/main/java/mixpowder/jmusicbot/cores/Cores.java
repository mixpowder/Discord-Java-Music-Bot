package mixpowder.jmusicbot.cores;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Cores{
	private final AudioPlayerManager playerManager;
	private GuildMusicManager musicManager;
	private  AudioManager audioManager;
	private Config config;

	public Cores(Config config){
		this.config = config;
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
		this.musicManager = new GuildMusicManager(playerManager);
	}

	public void setaudioManager(TextChannel channel){
		channel.getGuild().getAudioManager().setSendingHandler(musicManager.getSendHandler());
		audioManager = channel.getGuild().getAudioManager();
	}

	public void loadAndPlay(final TextChannel channel,final String trackUrl) {
		musicManager.player().setVolume(Integer.parseInt(config.getSettings("volume")));
	    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
	    	@Override
	    	public void trackLoaded(AudioTrack track) {
	    		channel.sendMessage(track.getInfo().title + "が再生リストに追加されました").queue();
	    		play(channel, track);
	    	}

	    	public void playlistLoaded(AudioPlaylist playlist) {
	    		for(AudioTrack track : playlist.getTracks()){
	    			play(channel, track);
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

	  	public void play(TextChannel channel, AudioTrack track) {
	  		musicManager.scheduler().queue(track,channel);
	  	}

	  	public void skipTrack() {
	  		musicManager.scheduler().nextTrack();
	  	}

	  	public AudioManager audioManager(){
	  		return audioManager;
	  	}

	  	public GuildMusicManager musicManager(){
	  		return musicManager;
	  	}

	  	public Config config(){
	  		return config;
	  	}

	  	public String node(String data){
	  		return config.getSettings(data);
	  	}

	  	public void setvolume(String data){
	  		config.setvolume(data);
	  	}
}