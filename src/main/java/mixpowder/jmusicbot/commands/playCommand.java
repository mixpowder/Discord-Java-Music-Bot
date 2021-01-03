package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class playCommand extends Command{

	private Cores core;

	public playCommand(Cores core){
		this.name = "play";
		this.help = "play <url>";
		this.core = core;
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
