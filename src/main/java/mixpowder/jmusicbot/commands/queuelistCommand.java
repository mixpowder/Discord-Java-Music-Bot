package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;

public class queuelistCommand extends Command{

	private Cores core;

	public queuelistCommand(Cores core){
		this.name = "queuelist";
		this.help = "queuelist 現在流す予定の曲のリストを表示";
		this.core = core;
	}

	@Override
	protected void execute(CommandEvent e) {
		e.reply(core.musicManager().scheduler().queuelist());
	}
}