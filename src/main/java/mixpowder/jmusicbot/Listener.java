package mixpowder.jmusicbot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import net.dv8tion.jda.api.JDA;

public class Listener  extends WindowAdapter implements ActionListener{

	private JDA jda;

	public Listener(JDA jda){
		this.jda = jda;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "停止"){
			this.jda.shutdown();
		}

	}

	public void windowClosing(WindowEvent e) {
		this.jda.shutdown();
		System.exit(0);

	}
}
