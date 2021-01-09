package mixpowder.jmusicbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import mixpowder.jmusicbot.cores.Cores;
import net.dv8tion.jda.api.managers.AudioManager;

public class leaveCommand extends Command{

	private Cores core;

	public leaveCommand(Cores core){
		this.core = core;
		this.name = core.node("leaveCommand");
		this.help = core.node("leaveDescription");
	}

	@Override
	protected void execute(CommandEvent e) {
		AudioManager audioManager = this.core.audioManager();
		if (!audioManager.isConnected()) {
			e.reply("ボイスチャンネルに接続されていません");
            return;
		}else{
			audioManager.closeAudioConnection();
		}
	}
}