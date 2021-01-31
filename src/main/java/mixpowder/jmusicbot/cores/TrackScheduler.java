package mixpowder.jmusicbot.cores;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.api.entities.TextChannel;


public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
	private final BlockingQueue<AudioTrack> queue;
	private final Cores core;
	private AudioTrack nowtrack;
	private Boolean bool = false;
	private TextChannel channel;


	public TrackScheduler(AudioPlayer player,Cores core) {
		this.core = core;
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
	}


	public void queue(AudioTrack track,TextChannel channel) {
		this.channel = channel;

		if (!this.player.startTrack(track, true)) {
			this.queue.offer(track);
		}else{
			this.nowtrack = track.makeClone();
		}
	}

	public void nextTrack() {
		this.nowtrack = this.queue.peek().makeClone();
		this.player.startTrack(this.queue.poll(), false);
	}

	public boolean shuffle(){
		AudioTrack[] array = new AudioTrack[this.queue.size()];
		AudioTrack tmp = null;
		Random rnd = new Random();

		if(this.queue.size() > 1){
			for(int i = 0; i < this.queue.size();i++){
				array[i] = (AudioTrack) this.queue.toArray()[i];
			}

			for (int i = 0; i < array.length; i++){
				int index = rnd.nextInt(array.length);
				tmp = array[i];
				array[i] = array[index];
				array[index] = tmp;
			}

			queuebreak();
			for(int i = 0; i < array.length; i++){
				if(array[i] != null){
					this.queue.offer(array[i]);
				}
			}

			return true;
		}else{
			return false;
		}
	}


	public String queuelist(){
		String list = "";
		int id = 1;
		for(AudioTrack track : this.queue){
			list += id + "番目: " + track.getInfo().title + "\n";
			id++;
		}
		return list;
	}

	public void queuebreak(){
		this.queue.removeAll(this.queue);
	}

	public void setRepeat(Boolean bool){
		this.bool = bool;
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			if(this.bool){
				player.startTrack(this.nowtrack.makeClone(),true);
			}else{
				nextTrack();
			}

		}
	}

	 public void onTrackStart(AudioPlayer player, AudioTrack track) {
		 this.channel.sendMessage(track.getInfo().title + "を再生します").queue();
	 }
}