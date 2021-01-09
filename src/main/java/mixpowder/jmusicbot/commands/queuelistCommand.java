package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;

public class queuelistCommand extends Command{

	private Cores core;

	public queuelistCommand(Cores core){
		this.core = core;
		this.name = core.node("queuelistCommand");
		this.help = core.node("queuelistDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		e.reply(core.musicManager().scheduler().queuelist());
	}
}