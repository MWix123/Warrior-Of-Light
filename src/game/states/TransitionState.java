package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.main.GamePanel;

public class TransitionState extends GameState{

	private int timer = 0, level, background;
	private Font font = new Font("Rockwell Extra Bold", Font.PLAIN, 48);
	private String levelName;
	
	public TransitionState(GameStateManager gsm, int levelPath, int backgroundPath) {
		super(gsm, levelPath, backgroundPath);
	}

	@Override
	public void init(int levelPath, int backgroundPath) {
		level = levelPath;
		background = backgroundPath;
		levelName = "Level " + (level+1);
	}

	@Override
	public void tick() {
		if(++timer >= 120) {
			gsm.states.clear();
			gsm.states.push(new LevelState(gsm, level, background));
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		if(timer < 60) {
			g.setColor(new Color(1f,1f,1f,0f+(timer/60f)));
		}else if(timer < 80) {
			g.setColor(new Color(1f,1f,1f,1f));
		}else{
			g.setColor(new Color(1f,1f,1f,1f-((timer-79)/40f)));
		}
		g.drawString(levelName, GamePanel.WIDTH/2 - g.getFontMetrics().stringWidth(levelName)/2,
					GamePanel.HEIGHT/2 );
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void mouseClicked(int k, int j) {
		
	}
}
