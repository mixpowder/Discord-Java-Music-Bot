package mixpowder.jmusicbot.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mixpowder.jmusicbot.Listener;
import net.dv8tion.jda.api.JDA;

public class Frame extends JFrame{

	public Frame(JDA jda){
		JButton button = new JButton("停止");
		Listener listener = new Listener(jda);
		JPanel panel = new JPanel();
		Container contentpane = getContentPane();

		setTitle("botコンソール");
		panel.add(button);
		contentpane.add(panel,BorderLayout.CENTER);

		button.addActionListener(listener);
		addWindowListener(listener);

		setBounds(250,250,250,80);

		setVisible(true);
	}

}
