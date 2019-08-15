package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {

	private BufferedImage image;
	
	private double ms,x;
	
	public Background(String path, double ms){
		this.ms = ms;
		try{
			image = ImageIO.read(this.getClass().getResource(path));
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(image, (int)x, 0, null);
		if(x < 0){
			g.drawImage(image, (int)x+GamePanel.WIDTH, 0,null);
			if(x < -image.getWidth()){
				x = 0;
			}
		}
		if(x > 0){
			g.drawImage(image, (int)x-GamePanel.WIDTH, 0,null);
			if(x > image.getWidth()){
				x = 0;
			}
		}
		x += ms;
	}
}
