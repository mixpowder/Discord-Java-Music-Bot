package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;

public class QueuelistCommand extends Command{

	private Cores core;

	public QueuelistCommand(Cores core){
		this.core = core;
		this.name = core.node("queuelistCommand");
		this.help = core.node("queuelistDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		String s = this.core.musicManager().scheduler().queuelist();
		if(s != ""){
			e.reply(s);
		}else{
			e.reply("待機中の曲はありません");
		}
	}
}