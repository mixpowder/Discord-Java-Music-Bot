package mixpowder.jmusicbot;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import mixpowder.jmusicbot.commands.leaveCommand;
import mixpowder.jmusicbot.commands.nowplayingCommand;
import mixpowder.jmusicbot.commands.playCommand;
import mixpowder.jmusicbot.commands.queuelistCommand;
import mixpowder.jmusicbot.commands.skipCommand;
import mixpowder.jmusicbot.commands.stopCommand;
import mixpowder.jmusicbot.commands.volumeCommand;
import mixpowder.jmusicbot.cores.Cores;
import mixpowder.jmusicbot.cores.config;
import mixpowder.jmusicbot.gui.ErrorFrame;
import mixpowder.jmusicbot.gui.Frame;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

	public static void main(String[] args) {
		Cores core = new Cores(new config());
		if(!(core.node("OwnerID").equals("ID") || core.node("BotToken").equals("Token"))){
			JDA jda = Bot(core);
			new Frame(jda);
		}else{
			new ErrorFrame("OwnerIDとBotTokenを設定してください");
		}
	}



	public static JDA Bot(Cores core){
		JDA jda = null;
		CommandClientBuilder cc = new CommandClientBuilder()
				.addCommands(new playCommand(core),
						new volumeCommand(core),
						new skipCommand(core),
						new leaveCommand(core),
						new stopCommand(core),
						new nowplayingCommand(core),
						new queuelistCommand(core))
				.setPrefix(core.node("prefix"))
				.setOwnerId(core.node("OwnerID"))
				.setActivity(Activity.streaming("La Campanella","https://www.youtube.com/watch?v=H1Dvg2MxQn8"));

		try {
			jda = (new JDABuilder())
					.setToken(core.node("BotToken"))
					.addEventListeners(cc.build())
					.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		return jda;
	}

}
