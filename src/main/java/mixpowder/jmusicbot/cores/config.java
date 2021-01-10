package mixpowder.jmusicbot.cores;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class config {


	private ObjectNode node;

	public config(){
		try {
			setConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setConfig() throws IOException{
		File jsonfile = new File("Settings.json");
		ObjectMapper mapper = new ObjectMapper();
		Settings settings = new Settings();
		String json = null;
		if(!jsonfile.exists()){
			jsonfile.createNewFile();
			json = mapper.writeValueAsString(settings);
		}else{
			json = mapper.writeValueAsString(mapper.readTree(jsonfile).deepCopy());
		}

		settings = mapper.readValue(json,Settings.class);
		json = mapper.writeValueAsString(settings);
		node = mapper.readTree(json).deepCopy();

		mapper.writer(new DefaultPrettyPrinter()).writeValue(jsonfile, node);

	}

	public String getSettings(String data){
		return node.get(data).textValue();
	}

	public void setvolume(String data){
		File jsonfile = new File("Settings.json");
		node.put("volume",data);
		try {
			(new ObjectMapper()).writer(new DefaultPrettyPrinter()).writeValue(jsonfile, node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


class Settings{

	public String OwnerID = "ID";
	public String BotToken = "Token";
	public String volume = "3";
	public String prefix = "!!";


	public String leaveCommand = "leave";
	public String leaveDescription = "ボイスチャンネルからbotを退出させます";

	public String nowplayingCommand = "nowplaying";
	public String nowplayingDescription = "現在流れている曲の名前を表示させます";

	public String playCommand = "play";
	public String playDescription = "play <url> 曲を再生させます";

	public String queuelistCommand = "queuelist";
	public String queuelistDescription = "現在流す予定の曲のリストを表示";

	public String skipCommand = "skip";
	public String skipDescription = "曲をスキップして次トラックに移動させます";

	public String stopCommand = "stop";
	public String stopDescription = "すべてのトラックを飛ばします";

	public String volumeCommand = "volume";
	public String volumeDescription = "volume <数値> 音量を設定します 初期は3です";

}