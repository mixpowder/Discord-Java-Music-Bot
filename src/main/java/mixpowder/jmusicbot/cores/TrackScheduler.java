package mixpowder.jmusicbot.cores;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;


public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
	private final BlockingQueue<AudioTrack> queue;


	public TrackScheduler(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
	}


	public void queue(AudioTrack track) {

		if (!player.startTrack(track, true)) {
			queue.offer(track);
		}
	}


	public void nextTrack() {
		player.startTrack(queue.poll(), false);
	}

	public String queuelist(){
		String list = "";
		int id = 1;
		for(AudioTrack track : queue){
			list += id + "番目: " + track.getInfo().title + "\n";
			id++;
		}
		return list;
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			nextTrack();
		}
	}
}