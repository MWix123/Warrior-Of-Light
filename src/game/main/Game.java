package game.main;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Warrior Of Light");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(new GamePanel(), BorderLayout.CENTER);
		frame.pack(); //pack after adding panel
		frame.setLocationRelativeTo(null); //set location after pack
		frame.setVisible(true); //ALWAYS SET VISIBLE LAST
		
		SpriteSheet ss = new SpriteSheet(new ImageLoader().loadImage("/res/GameSheet1.png"));

		//the image must be directly under the src folder (root when using getResource())
		//ImageIcon icon = new ImageIcon(frame.getClass().getResource("/res/Warrior1.png"));
		ImageIcon icon = new ImageIcon(ss.crop(24, 1, 32, 48));
		frame.setIconImage(icon.getImage());
	}
}
