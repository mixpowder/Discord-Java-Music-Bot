package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;

public class ShuffleCommand extends Command{

	private Cores core;

	public ShuffleCommand(Cores core){
		this.core = core;
		this.name = core.node("shuffleCommand");
		this.help = core.node("shuffleDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		if(this.core.musicManager().scheduler().shuffle()){
			e.reply("キュー内の曲をシャッフルしました");
		}else{
			e.reply("シャッフルできませんでした");
		}
	}
}