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
		AudioSourceManagers.registerRemoteSources(this.playerManager);
		AudioSourceManagers.registerLocalSource(this.playerManager);
		this.musicManager = new GuildMusicManager(this.playerManager,this);
	}

	public void setaudioManager(TextChannel channel){
		channel.getGuild().getAudioManager().setSendingHandler(this.musicManager.getSendHandler());
		this.audioManager = channel.getGuild().getAudioManager();
	}

	public void loadAndPlay(final TextChannel channel,final String trackUrl) {
		this.musicManager.player().setVolume(Integer.parseInt(this.config.getSettings("volume")));
	    this.playerManager.loadItemOrdered(this.musicManager, trackUrl, new AudioLoadResultHandler() {
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
	  		this.musicManager.scheduler().queue(track,channel);
	  	}

	  	public void skipTrack() {
	  		this.musicManager.scheduler().nextTrack();
	  	}

	  	public AudioManager audioManager(){
	  		return this.audioManager;
	  	}

	  	public GuildMusicManager musicManager(){
	  		return this.musicManager;
	  	}

	  	public Config config(){
	  		return this.config;
	  	}

	  	public String node(String data){
	  		return this.config.getSettings(data);
	  	}

	  	public void setvolume(String data){
	  		this.config.setvolume(data);
	  	}
}