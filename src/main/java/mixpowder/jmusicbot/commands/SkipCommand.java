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
		if(this.core.musicManager().scheduler().nextTrack()){
			e.reply("次のトラックへ移動します");
		}else{
			e.reply("次のトラックが存在しないため終了しました");
		}
	}

}
