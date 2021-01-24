package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class VolumeCommand extends Command{

	private Cores core;

	public VolumeCommand(Cores core){
		this.core = core;
		this.name = core.node("volumeCommand");
		this.help = core.node("volumeDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		String[] data = e.getMessage().getContentRaw().split(" ");
		if(data.length == 2){
			if(data[1].matches("[+-]?\\d*(\\.\\d+)?")){
				e.reply("音量を" + this.core.musicManager().player().getVolume() + "から" + data[1] + "に変更します");
				this.core.musicManager().player().setVolume(Integer.parseInt(data[1]));
				this.core.setvolume(data[1]);
			}else{
				e.reply("引数は数値にしてください");
			}
		}else{
			e.reply("引数を設定してください");
		}
	}
}
