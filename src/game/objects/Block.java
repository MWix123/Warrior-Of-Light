package game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.main.GamePanel;
import game.main.SpriteSheet;
import game.states.GameState;

public class Block extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	private BufferedImage blockImage;
	InputStream in;
	Clip noise;
	
	public static final int size = 32;
	public int id;
	private String blockType;
	
	private int xSize, ySize;
	
	public Block(int x, int y, int id, SpriteSheet ss){
		this.id = id;
		
		xSize = size;
		ySize = size;
		
		switch(id){		
			case 1:
				blockImage = ss.crop(13, 0, 16, 16);
				break;
			case 2:
				blockImage = ss.crop(7, 0, 16, 16);
				break;
			case 3:
				blockImage = ss.crop(0, 7, 32, 32);
				break;
			case 4: //level end flag
				blockImage = ss.crop(0, 9, 32, 32);
				break;
			case 5: //Brick Block 1
				blockImage = ss.crop(2, 7, 32, 32);
				break;
			case 6: //Brick Block 2
				blockImage = ss.crop(4, 7, 32, 32);
				break;
			case 7: //Power Up
				blockImage = ss.crop(4, 4, 16, 16);
				break;
			case 8:
				blockImage = ss.crop(14, 0, 16, 16);
				break;
			case 9: //spike
				blockImage = ss.crop(2, 10, 32, 16);
				//blockType = "/res/Spike.png";
				break;
			case 10: //enemy 
				blockImage = ss.crop(17, 0, 16, 16);
				break;
			case 11:
				blockImage = ss.crop(12, 0, 16, 16);
				break;
			case 12:
				blockImage = ss.crop(15, 0, 16, 16);
				break;
			case 13:
				blockImage = ss.crop(16, 0, 16, 16);
				break;
			case 14: //bush
				blockImage = ss.crop(0, 11, 48, 32);
				xSize = 3*size;
				ySize = 2*size;
				break;
			default:
				break;
		}
		
		/*if(id > 8 && id < 10){
			try {
				blockImage = ImageIO.read(this.getClass().getResourceAsStream(blockType));
			} catch (IOException e){
				e.printStackTrace();
			}
		}*/		

		setBounds(x,y,xSize,ySize);
	}
	
	public int getId(){
		return id;
	}
	
	public void blockNoise(){
		try {
			AudioInputStream aud = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream("/res/BlockNoise.wav"));
			noise = AudioSystem.getClip();
			noise.open(aud);
			noise.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics g){
		//g.setColor(new Color(0,200,0));
		if(x + 160 > GameState.xOffset && x < GameState.xOffset + GamePanel.WIDTH + 160
				&& y + 160 > GameState.yOffset && y < GameState.yOffset + GamePanel.HEIGHT + 160){
			g.drawImage(blockImage, x - (int)GameState.xOffset,
					y - (int)GameState.yOffset, xSize, ySize, null);
		}
		
		
	}
}
