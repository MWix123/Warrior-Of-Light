package game.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public BufferedImage loadImage(String path){
		try{
			return ImageIO.read(this.getClass().getResource(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
