package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class skipCommand extends Command{

	private Cores core;

	public skipCommand(Cores core){
		this.name = "skip";
		this.help = "skip 曲をスキップして次トラックに移動させます";
		this.core = core;
	}

	@Override
	protected void execute(CommandEvent e) {
		this.core.skipTrack();
		e.reply("次のトラックに移動します");
	}

}
