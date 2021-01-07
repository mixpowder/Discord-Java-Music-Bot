package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import mixpowder.jmusicbot.cores.Cores;

public class nowplayingCommand extends Command {

	private Cores core;

	public nowplayingCommand(Cores core){
		this.name = "nowplaying";
		this.help = "nowplaying 現在流れている曲の名前を表示させます";
		this.core = core;
	}

	@Override
	protected void execute(CommandEvent e) {
		AudioTrack track = this.core.musicManager().player().getPlayingTrack();
		e.reply("今現在 " + track.getInfo().title + " を再生中");
	}
}
