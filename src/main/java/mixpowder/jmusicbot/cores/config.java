package mixpowder.jmusicbot.cores;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class config {

	private Config config;

	public config(){
		this.config = setConfig();
	}

	private Config setConfig(){
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
		config = ConfigFactory.parseFile(file);
		if(config.getString("OwnerID").equals("ID") || config.getString("BotToken").equals("Token")){
			return null;
		}
		return config;
	}

	public String getToken(){
		return config.getString("BotToken");
	}

	public String getOwnerID(){
		return config.getString("OwnerID");
	}

	public Config getConfig(){
		return config;
	}

}
