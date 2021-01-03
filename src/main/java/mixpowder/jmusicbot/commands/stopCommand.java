package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;

public class stopCommand extends Command{

	private Cores core;

	public stopCommand(Cores core){
		this.name = "stop";
		this.help = "stop";
		this.core = core;
	}

	@Override
	protected void execute(CommandEvent e) {
		this.core.musicManager.player.destroy();
		e.reply("曲を終了させました");
	}

}
