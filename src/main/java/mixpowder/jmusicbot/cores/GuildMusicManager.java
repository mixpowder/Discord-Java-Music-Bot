package mixpowder.jmusicbot.cores;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;


public class GuildMusicManager {

	private final AudioPlayer player;
	private final TrackScheduler scheduler;

	public GuildMusicManager(AudioPlayerManager manager,Cores core) {
		this.player = manager.createPlayer();
		this.scheduler = new TrackScheduler(this.player,core);
		this.player.addListener(this.scheduler);
	}

	public AudioPlayerSendHandler getSendHandler() {
		return new AudioPlayerSendHandler(this.player);
	}

	public TrackScheduler scheduler(){
		return this.scheduler;
	}

	public AudioPlayer player(){
		return this.player;
	}
}