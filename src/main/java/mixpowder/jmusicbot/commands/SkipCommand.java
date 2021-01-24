package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class SkipCommand extends Command{

	private Cores core;

	public SkipCommand(Cores core){
		this.core = core;
		this.name = core.node("skipCommand");
		this.help = core.node("skipDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		this.core.skipTrack();
		e.reply("次のトラックへ移動します");
	}

}
