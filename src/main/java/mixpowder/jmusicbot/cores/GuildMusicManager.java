package mixpowder.jmusicbot.cores;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;


public class GuildMusicManager {

	private final AudioPlayer player;
	private final TrackScheduler scheduler;

	public GuildMusicManager(AudioPlayerManager manager) {
		player = manager.createPlayer();
		scheduler = new TrackScheduler(player);
		player.addListener(scheduler);
	}

	public AudioPlayerSendHandler getSendHandler() {
		return new AudioPlayerSendHandler(player);
	}

	public TrackScheduler scheduler(){
		return scheduler;
	}

	public AudioPlayer player(){
		return player;
	}
}