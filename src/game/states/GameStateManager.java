package game.states;

import java.awt.Graphics;
import java.util.Stack;

public class GameStateManager {

	public Stack<GameState> states; //creates a list of states for the manager
	private int currentLevel = 0;
	
	public GameStateManager(){
		states = new Stack<GameState>();
		states.push(new MenuState(this, 0, 0));
	}
	
	public void tick(){
		states.peek().tick(); //calls the function from the top state in the stack
	}
	
	public void draw(Graphics g){
		states.peek().draw(g);
	}
	
	public void keyPressed(int k){
		states.peek().keyPressed(k);
	}
	
	public void keyReleased(int k){
		states.peek().keyReleased(k);
	}
	
	public void mouseClicked(int k, int j){
		states.peek().mouseClicked(k,j);
	}
	
	public int getNextLevel() {
		return ++currentLevel;
	}
	
	public void returnToFirst() {
		currentLevel = 0;
	}
	
}
