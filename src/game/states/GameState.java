package game.states;

import java.awt.Graphics;

public abstract class GameState {//abstract means it is just a blueprint for all other states

	protected GameStateManager gsm;
	public static double xOffset, yOffset;
	
	protected String[] levelNames = {"/Level1.png", "/Level2.png"};
	protected String[] backgroundNames = {"/res/Background2.png"};
	
	public GameState(GameStateManager gsm, int levelPath, int backgroundPath){
		this.gsm = gsm;
		GameState.xOffset = 0;
		GameState.yOffset = 0;
		init(levelPath, backgroundPath);
	}
	
	public abstract void init( int levelPath, int backgroundPath);
	public abstract void tick();
	public abstract void draw(Graphics g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mouseClicked(int k,int j);
}
