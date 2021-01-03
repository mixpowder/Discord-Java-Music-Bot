package mixpowder.jmusicbot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import mixpowder.jmusicbot.commands.playCommand;
import mixpowder.jmusicbot.commands.skipCommand;
import mixpowder.jmusicbot.commands.volumeCommand;
import mixpowder.jmusicbot.cores.Cores;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

	public static void main(String[] args) {
		Config config = file();
		if(!(config == null)){
			JDA jda = Bot(config);
			Frame(jda);
		}
	}

	public static void Frame(JDA jda){
		JFrame frame = new JFrame("botコンソール");
		JButton button = new JButton("停止");
		Listener listener = new Listener(jda);
		JPanel panel = new JPanel();
		Container contentpane = frame.getContentPane();
		panel.add(button);
		contentpane.add(panel,BorderLayout.CENTER);

		button.addActionListener(listener);
		frame.addWindowListener(listener);

		frame.setBounds(250,250,250,80);

		frame.setVisible(true);
	}

	public static void ErrorFrame(String message){
		JFrame frame = new JFrame("エラー");
		JLabel label = new JLabel(message);
		frame.add(label);

		frame.setBounds(250,250,250,80);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static Config file(){
		File file = new File("config.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file);
				writer.println("OwnerID=ID\nBotToken=Token");
				writer.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Config config = ConfigFactory.parseFile(file);
		if(config.getString("OwnerID").equals("ID") || config.getString("BotToken").equals("Token")){
			ErrorFrame("OwnerIDとBotTokenを設定してください");
			return null;
		}

		return config;
	}


	public static JDA Bot(Config config){
		Cores core = new Cores();
		JDA jda = null;
		CommandClientBuilder cc = new CommandClientBuilder()
				.addCommands(new playCommand(core),new volumeCommand(core),new skipCommand(core))
				.setPrefix("!!")
				.setOwnerId(config.getString("OwnerID"))
				.setActivity(Activity.streaming("La Campanella","https://www.youtube.com/watch?v=H1Dvg2MxQn8"));

		try {
			jda = (new JDABuilder())
					.setToken(config.getString("BotToken"))
					.addEventListeners(cc.build())
					.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		return jda;
	}

}
