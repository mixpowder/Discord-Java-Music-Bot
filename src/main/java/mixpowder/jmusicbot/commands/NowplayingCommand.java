package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import mixpowder.jmusicbot.cores.Cores;

public class NowplayingCommand extends Command {

	private Cores core;

	public NowplayingCommand(Cores core){
		this.core = core;
		this.name = core.node("nowplayingCommand");
		this.help = core.node("nowplayingDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		AudioTrack track = this.core.musicManager().player().getPlayingTrack();
		e.reply("今現在 " + track.getInfo().title + " を再生中");
	}
}
