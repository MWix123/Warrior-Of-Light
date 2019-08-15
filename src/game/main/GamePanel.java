package game.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import game.states.GameStateManager;

public class GamePanel extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 900;
	public static final int HEIGHT = 450;
	
	private Thread thread; //must implement runnable to use thread
	private boolean running = false;
	
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	private GameStateManager gsm; //uses the current state to determine what happens 
	
	public GamePanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		addKeyListener(this);
		setFocusable(true);
		
		start();
	}

	private void start(){
		running = true;
		thread = new Thread(this);
		thread.start(); //calls the run method
		
	}
	
	public void run() {
		long start, elapsed, wait;
		
		gsm = new GameStateManager();
		
		while(running){
			start = System.nanoTime();
			
			tick();
			render(); //built-in method for paintComponent
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed/1000000;//divide by 1 million bc sleep() requires millis 
			
			if(wait <= 0){
				wait = 5;
			}
			
			try{
				Thread.sleep(wait); //Thread the object, not the thread created
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void tick(){ //updates game logic
		gsm.tick();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		gsm.draw(g);
		
		g.dispose();
		bs.show();
		
	}
	
	
	public void keyPressed(KeyEvent e){
		gsm.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e){
		gsm.keyReleased(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e){
		
	}
}
