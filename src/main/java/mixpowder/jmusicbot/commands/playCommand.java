package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class playCommand extends Command{

	private Cores core;

	public playCommand(Cores core){
		this.core = core;
		this.name = core.node("playCommand");
		this.help = core.node("playDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		String[] url = e.getMessage().getContentRaw().split(" ");
		if(url.length == 2){
			core.loadAndPlay(e.getTextChannel(),url[1]);
		}else{
			e.reply("引数を設定してください");
		}
	}

}
