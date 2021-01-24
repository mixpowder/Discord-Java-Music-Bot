package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;


public class PlayCommand extends Command{

	private Cores core;

	public PlayCommand(Cores core){
		this.core = core;
		this.name = core.node("playCommand");
		this.help = core.node("playDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		String[] url = e.getMessage().getContentRaw().split(" ");
		if(url.length == 2){
			if(e.getMember().getVoiceState().getChannel() != null){
				if(this.core.audioManager() == null || !this.core.audioManager().isConnected()){
					this.core.setaudioManager(e.getTextChannel());
					this.core.audioManager().openAudioConnection(e.getMember().getVoiceState().getChannel());
				}
				this.core.loadAndPlay(e.getTextChannel(),url[1]);
			}else{
				e.reply("ボイスチャンネルに接続してから実行してください");
			}
		}else{
			e.reply("引数を設定してください");
		}
	}

}
