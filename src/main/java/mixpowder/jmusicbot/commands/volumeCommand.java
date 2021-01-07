package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class volumeCommand extends Command{

	private Cores core;

	public volumeCommand(Cores core){
		this.name = "volume";
		this.help = "volume <数値> 音量を設定します 初期は3です";
		this.core = core;
	}

	@Override
	protected void execute(CommandEvent e) {
		String[] data = e.getMessage().getContentRaw().split(" ");
		if(data.length == 2){
			if(data[1].matches("[+-]?\\d*(\\.\\d+)?")){
				this.core.musicManager().player().setVolume(Integer.parseInt(data[1]));
				e.reply("音量を" + this.core.musicManager().player().getVolume() + "から" + data[1] + "に変更します");
			}else{
				e.reply("引数は数値にしてください");
			}
		}else{
			e.reply("引数を設定してください");
		}
	}
}
