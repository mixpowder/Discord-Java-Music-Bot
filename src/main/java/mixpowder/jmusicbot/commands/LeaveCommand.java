package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends Command{

	private Cores core;

	public LeaveCommand(Cores core){
		this.core = core;
		this.name = core.node("leaveCommand");
		this.help = core.node("leaveDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		AudioManager audioManager = this.core.audioManager();
		if (audioManager == null || !audioManager.isConnected()) {
			e.reply("ボイスチャンネルに接続されていません");
		}else{
			audioManager.closeAudioConnection();
			this.core.musicManager().player().destroy();
			this.core.musicManager().scheduler().queuebreak();
			e.reply("ボイスチャンネルから切断しました");
		}
	}
}