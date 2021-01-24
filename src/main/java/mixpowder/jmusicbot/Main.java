package mixpowder.jmusicbot;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import mixpowder.jmusicbot.commands.LeaveCommand;
import mixpowder.jmusicbot.commands.NowplayingCommand;
import mixpowder.jmusicbot.commands.PlayCommand;
import mixpowder.jmusicbot.commands.QueuelistCommand;
import mixpowder.jmusicbot.commands.ShuffleCommand;
import mixpowder.jmusicbot.commands.SkipCommand;
import mixpowder.jmusicbot.commands.StopCommand;
import mixpowder.jmusicbot.commands.VolumeCommand;
import mixpowder.jmusicbot.cores.Config;
import mixpowder.jmusicbot.cores.Cores;
import mixpowder.jmusicbot.gui.ErrorFrame;
import mixpowder.jmusicbot.gui.Frame;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

	public static void main(String[] args) {
		Cores core = new Cores(new Config());
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
				.addCommands(new PlayCommand(core),
						new VolumeCommand(core),
						new SkipCommand(core),
						new LeaveCommand(core),
						new StopCommand(core),
						new NowplayingCommand(core),
						new QueuelistCommand(core),
						new ShuffleCommand(core))
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
