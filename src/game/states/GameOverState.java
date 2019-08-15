package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game.main.GamePanel;

public class GameOverState extends GameState{

	public GameOverState(GameStateManager gsm, int levelPath, int backgroundPath){
		super(gsm, 0, 0);
	}

	public void init(int levelPath, int backgroundPath){
		gsm.returnToFirst();
	}

	public void tick(){
		
	}

	public void draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.PLAIN, 72));
		g.drawString("Game Over", GamePanel.WIDTH / 3 - 55, GamePanel.HEIGHT / 2);
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		g.drawString("Press Enter to return to the home screen.", GamePanel.WIDTH / 3 - 85,
				2 * GamePanel.HEIGHT / 3);
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
