package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.entities.Player;
import game.main.Background;
import game.main.GamePanel;
import game.main.ImageLoader;
import game.main.SpriteSheet;
import game.mapping.Level;

public class LevelState extends GameState{

	private Player player;
	//private Map map;
	private Level level; //DSFNBJKLHBFJ
	private BufferedImage img, levelImg;
	private SpriteSheet ss;
	private Background background;
	
	private ImageLoader loader;
	InputStream in;
	Clip music;
	
	public static boolean pauseState = false;
	private boolean displayHelpMenu = false, backToHome = false;
	private int pauseSelection = 0;

	private String[] pauseOptions = {"Resume", "Help", "Quit"};
	
	private String[] commands = {"Left Arrow - Move Left", "Right Arrow - Move Right", 
			"Z - Shoot", "Shift - Increase speed", "Space - Jump", "Enter - Pause"};
	
	public LevelState(GameStateManager gsm, int levelPath, int backgroundPath) {
		super(gsm, levelPath, backgroundPath);
	}

	public void init(int levelPath, int backgroundPath){
		loader = new ImageLoader();
		img = loader.loadImage("/res/GameSheet1.png");
		levelImg = loader.loadImage(levelNames[levelPath]);
		
		ss = new SpriteSheet(img);
		player = new Player(32,46, 150, 4750, ss);

		background = new Background(backgroundNames[backgroundPath], -0.5);
		level = new Level(levelImg, ss);
		
		xOffset = 150;
		yOffset = 4750;
		
		try {
			AudioInputStream aud = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/Creepy.wav"));			
			try {
				music = AudioSystem.getClip();
				music.open(aud);
				music.loop(Clip.LOOP_CONTINUOUSLY);
			}catch (LineUnavailableException e){
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}

	public void tick(){
		if(!Player.paused){
			player.tick(level.getBlocks(), level.getMovingBlocks());
			level.tick();
		}
		
		if(Player.gameOver){
			Player.playerLife = 3;
			Player.gameOver = false;
			gsm.states.clear();
			gsm.states.push(new GameOverState(gsm, 0, 0));
			try{
				music.stop();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(player.levelEnd){
			try{
				music.stop();
			}catch(Exception e){
				e.printStackTrace();
			}
			gsm.states.clear();
			gsm.states.push(new TransitionState(gsm, gsm.getNextLevel(), 0));
		}
		
		if(backToHome){
			Player.playerLife = 3;
			gsm.returnToFirst();
			gsm.states.clear();
			gsm.states.push(new MenuState(gsm, 0, 0));
		}
	}

	public void draw(Graphics g){
		background.draw(g);		
		level.draw(g);
		player.draw(g);
		if(pauseState) {
			g.setColor(new Color(255,0,0,100));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.PLAIN, 32));
			
			if(displayHelpMenu) {
				g.setFont(new Font("Arial", Font.PLAIN, 24));

				g.setColor(Color.white);
				for(int i = 0; i < commands.length; i++){
					g.drawString(commands[i], GamePanel.WIDTH / 3 + 30, 75 + i * 50);
				}				

				g.drawString("Press Enter to go back",GamePanel.WIDTH / 3 + 30, 400);
				
			}else {
				for(int i=0; i < pauseOptions.length; i++){
					if(i == pauseSelection){
						g.setColor(Color.yellow);
					}else{
						g.setColor(Color.white);
					}
					g.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 32));
					g.drawString(pauseOptions[i], GamePanel.WIDTH / 2 - 60, 180 + i * 55);
	
				}
			}
		}
	}

	public void keyPressed(int k){
		if(pauseState) {
			if(k == KeyEvent.VK_DOWN) {
				if(pauseSelection < 2) {
					pauseSelection++;
				}
			}else if(k == KeyEvent.VK_UP) {
				if(pauseSelection > 0) {
					pauseSelection--;
				}
			}
			
			if(k == KeyEvent.VK_ENTER) {
				if(pauseSelection == 0) {
					pauseState = false;
				}else if(pauseSelection == 1) {
					displayHelpMenu = !displayHelpMenu;
				}else if(pauseSelection == 2) {
					backToHome = true;
					pauseState = false;
				}
			}
		}else {
			if(k == KeyEvent.VK_ENTER){
				if(!pauseState){
					pauseState = true;
				}
			}
		}
		player.keyPressed(k);
	}
	
	public void keyReleased(int k){
		player.keyReleased(k);
	}

	public void mouseClicked(int k, int j){
		
	}

}
