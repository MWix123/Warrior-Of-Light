package game.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int col, int row, int w, int h){
		return sheet.getSubimage(16*col, 16*row, w, h);
	}
}
