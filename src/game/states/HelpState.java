package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game.main.GamePanel;

public class HelpState extends GameState{

	private String[] commands = {"Left Arrow - Move Left", "Right Arrow - Move Right", 
			"Z - Shoot", "Shift - Increase speed", "Space - Jump", "Enter - Pause"};
	
	private String goBack = "Press Enter to return to the Home Screen";
	
	public HelpState(GameStateManager gsm, int levelPath, int backgroundPath){
		super(gsm, 0, 0);
	}
	
	public void init(int levelPath, int backgroundPath){
		
	}

	public void tick(){
		
	}

	public void draw(Graphics g){
		g.setColor(new Color(200,0,0));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setFont(new Font("Arial", Font.PLAIN, 24));

		g.setColor(Color.yellow);
		for(int i = 0; i < commands.length; i++){
			g.drawString(commands[i], GamePanel.WIDTH / 3 + 30, 75 + i * 50);
		}

		g.drawString(goBack,GamePanel.WIDTH / 4 + 10, 400);
		
		//g.drawLine(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT);
	}

	public void keyPressed(int k){
		if(k == KeyEvent.VK_ENTER){
			gsm.states.push(new MenuState(gsm, 0, 0));
		}
		
	}

	public void keyReleased(int k){
		
	}
	
	public void mouseClicked(int k, int j){
		
	}

}
