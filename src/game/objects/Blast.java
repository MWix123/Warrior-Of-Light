package game.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.main.GamePanel;
import game.main.SpriteSheet;
import game.physics.Collision;
import game.states.GameState;

public class Blast extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	Clip music;
	AudioInputStream aud;
	private SpriteSheet ss;
	private BufferedImage bulletImage;

	private double bX, bY, bulletSpeed, currentXOffset, currentYOffset;
	
	private int direction, bulletXOffset, bulletYOffset;
	private boolean hitBlock, reverse;
	
	public Blast(){
		
	}
	
	public void init(int k, int j, int dir, SpriteSheet ss){
		bX = k;
		bY = j;
		direction = dir;
		
		currentXOffset = GameState.xOffset;
		currentYOffset = GameState.yOffset;
		
		bulletSpeed = 2;

		bulletImage = ss.crop(3, 4, 16, 16);
		
		try {
			aud = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream("/res/BlastNoise.wav"));
			music = AudioSystem.getClip();
			music.open(aud);
			music.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void tick(double speed, Block[][] b, ArrayList<MovingBlock> movingBlocks){
		if(direction <= 11){
			bX+=bulletSpeed*speed;
			reverse = false;
		}
		else{
			bX-=bulletSpeed*speed;
			reverse = true;
		}
		
		//bulletSpeed += 0.1;
		
		for(int i = 0; i < b.length; i++){
			
			for(int j = 0; j < b[0].length; j++){
				if(b[i][j].id != 0){
					//right collision(left side of block)
					if(Collision.playerBlock(new Point((int)bX + (int)GameState.xOffset + 10 + bulletXOffset, (int)bY + (int)GameState.yOffset + bulletYOffset), b[i][j]) || Collision.playerBlock(
									new Point((int)bX + (int)GameState.xOffset + 10 + bulletXOffset, (int)bY + (int)GameState.yOffset + 10 + bulletYOffset), b[i][j])){
						hitBlock = true;
						if(b[i][j].id == 5 || b[i][j].id == 10){
							b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
							b[i][j].blockNoise();
						}
					}
					
					//left collision(right side of block)
					if(Collision.playerBlock(new Point((int)bX + (int)GameState.xOffset + bulletXOffset, 
							(int)bY + (int)GameState.yOffset + bulletYOffset), b[i][j]) || Collision.playerBlock(
									new Point((int)bX + (int)GameState.xOffset + bulletXOffset,
											(int)bY + 10 + (int)GameState.yOffset + bulletYOffset), b[i][j])){
						hitBlock = true;
						if(b[i][j].id == 5 || b[i][j].id == 10){
							b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
							b[i][j].blockNoise();
						}
					}					
				}
			}
		}
		
		for(int i = 0; i < movingBlocks.size(); i++){
			if(movingBlocks.get(i).getId() != 0){
				if(Collision.playerMovingBlock(new Point((int)bX + (int)GameState.xOffset + 10 + bulletXOffset, (int)bY + (int)GameState.yOffset + bulletYOffset), movingBlocks.get(i)) || Collision.playerMovingBlock(
					new Point((int)bX + (int)GameState.xOffset + 10 + bulletXOffset, (int)bY + (int)GameState.yOffset + 10 + bulletYOffset), movingBlocks.get(i))){
					hitBlock = true;
					if(movingBlocks.get(i).getId() == 1){
						movingBlocks.remove(i);
						//noise
					}
				}
				
				//left collision(right side of block)
				if(Collision.playerMovingBlock(new Point((int)bX + (int)GameState.xOffset + bulletXOffset, 
					(int)bY + (int)GameState.yOffset + bulletYOffset), movingBlocks.get(i)) || Collision.playerMovingBlock(
							new Point((int)bX + (int)GameState.xOffset + bulletXOffset,
									(int)bY + 10 + (int)GameState.yOffset + bulletYOffset), movingBlocks.get(i))){
					hitBlock = true;
					if(movingBlocks.get(i).getId() == 1){
						movingBlocks.remove(i);
					}
				}
			}
		}
	}
	
	public void draw(Graphics g){
		bulletXOffset = (int)(currentXOffset - GameState.xOffset);
		bulletYOffset = (int)(currentYOffset - GameState.yOffset);
		
		/*g.setColor(Color.black);
		g.fillOval((int)bX + bulletXOffset, (int)bY + bulletYOffset, 10, 10);*/
		if(!reverse){
			g.drawImage(bulletImage, (int)bX + bulletXOffset, (int)bY + bulletYOffset, null);
		}else{
			g.drawImage(bulletImage, (int)bX + bulletXOffset + 16, (int)bY + bulletYOffset, -16, 16, null);
		}
	}
	
	public boolean isShooting(boolean shooting){
		if(bX < GamePanel.WIDTH && bX > 0 && hitBlock != true){
			shooting = true;
		}
		else{
			shooting = false;
			hitBlock = false;
			//System.out.println("Bullet gone." + GameState.xOffset);
		}
		return shooting;
	}
}
