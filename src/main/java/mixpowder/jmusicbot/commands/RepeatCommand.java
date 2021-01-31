package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;

public class RepeatCommand extends Command{

	private Cores core;

	public RepeatCommand(Cores core){
		this.core = core;
		this.name = core.node("repeatCommand");
		this.help = core.node("repeatDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		String[] s = e.getMessage().getContentRaw().split(" ");
		String repeat = "off";
		if(s.length == 2){
			Boolean bool = (s[1].equals("true"))?true : false;
			repeat = (bool)? "on" : "off";
			this.core.musicManager().scheduler().setRepeat(bool);
			e.reply("リピートを" + repeat + "にしました");
		}else{
			e.reply("引数を設定してください");
		}
	}
}