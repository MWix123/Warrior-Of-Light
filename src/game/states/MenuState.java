package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.main.GamePanel;
import game.main.ImageLoader;

public class MenuState extends GameState{
	
	private String[] options = {"Play", "Help", "Options", "Exit"};
	private int currentSelection = 0; //represents current menu option

	AudioInputStream aud;
	Clip music;
	private ImageLoader loader;
	public BufferedImage menuBackground;
	public Color color;
	
	public MenuState(GameStateManager gsm, int levelPath, int backgroundPath){
		super(gsm, 0, 0); //super = constructor 
	}
	
	public void init(int levelPath, int backgroundPath){
		loader = new ImageLoader();
		color = new Color(1f,0f,0f,0.5f);
		try {
			aud = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/CoolMusic.wav"));
			music = AudioSystem.getClip();
			music.open(aud);
			music.loop(Clip.LOOP_CONTINUOUSLY);
			menuBackground = loader.loadImage("/res/Title.png");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void tick(){}

	public void draw(Graphics g){
		g.drawImage(menuBackground, 0, 0, null);
		for(int i=0; i < options.length; i++){
			if(i == currentSelection){
				g.setColor(Color.yellow);
			}else{
				g.setColor(color);
			}
			
			g.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 32));
			g.drawString(options[i], GamePanel.WIDTH / 2 - 60, 180 + i * 55);

		}
	}

	public void keyPressed(int k){
		if(k == KeyEvent.VK_DOWN){
			if(currentSelection < options.length - 1){
				currentSelection++;
			}
		}else if(k == KeyEvent.VK_UP){
			if(currentSelection > 0){
				currentSelection--;
			}
		}
		
		if(k == KeyEvent.VK_ENTER){
			try{
				music.stop();
			}catch(Exception e){
				e.printStackTrace();
			}
			if(currentSelection == 0){
				gsm.states.push(new TransitionState(gsm, 0, 0));
			}else if(currentSelection == 1){
				gsm.states.push(new HelpState(gsm, 0, 0));
			}else if(currentSelection == 2){
				gsm.states.push(new OptionsState(gsm, 0, 0));
			}
			else if(currentSelection == 3){
				System.exit(0);
			}
		}
	}

	public void keyReleased(int k){
		
	}

	@Override
	public void mouseClicked(int k, int j) {
		// TODO Auto-generated method stub
		
	}

}
