package game.mapping;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.main.SpriteSheet;
import game.objects.Block;
import game.objects.MovingBlock;

public class Level {
	//creates a "pixel grid"
	private int[][] tiles;
	private Block[][] blocks;
	private SpriteSheet ss;
	private ArrayList<MovingBlock> movingBlocks;
	private int w, h, numMovingBlocks;
	
	public Level(BufferedImage map, SpriteSheet ss){
		w = map.getWidth();
		h = map.getHeight();
		this.ss = ss;
		
		createMap(map);
	}
	
	public void createMap(BufferedImage map){
		tiles = new int[w][h];
		blocks = new Block[w][h];		

		movingBlocks = new ArrayList<MovingBlock>();
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				//pixel coordinates for image
				Color c = new Color(map.getRGB(x, y));
				//%02x means 02 for show 2 places and x means its hexidecimal
				String s = String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
				
				int temp = numMovingBlocks;
				switch(s){
					case "00fff6": //nothing
						tiles[x][y] = 0;
						break;
					case "00ff00": // top grass
						tiles[x][y] = 1;
						break;
					case "7d6b2e": // regular dirt
						tiles[x][y] = 2;
						break;
					case "ffc603": //item block powerup
						tiles[x][y] = 3;
						break;
					case "ff0000": //level flag
						tiles[x][y] = 4;
						break;
					case "dfd3a9": //breakable brick block
						tiles[x][y] = 5;
						break;
					case "ffffff": //unbreakable block
						tiles[x][y] = 6;
						break;
					case "fff000": //powerup
						tiles[x][y] = 7;
						break;
					case "378537": //left grass block
						tiles[x][y] = 8;
						break;
					case "cedcda": //spike
						tiles[x][y] = 9;
						break;
					case "494330": //left cliff
						tiles[x][y] = 11;
						break;
					case "3c3313": //right cliff
						tiles[x][y] = 12;
						break;
					case "004900": //right grass block
						tiles[x][y] = 13;
						break;
					case "0000ff": //bush
						tiles[x][y] = 14;
						break;
					case "000000": //enemy 1
						tiles[x][y] = 1;
						numMovingBlocks++;
						break;
					case "ffff00": //Moving Grass Block forward
						tiles[x][y] = 2;
						numMovingBlocks++;
						break;
					case "cccc00": //Moving grass block reverse
						tiles[x][y] = 3;
						numMovingBlocks++;
						break;
					default:
						tiles[x][y] = 0;
						break;
				}
				
				// creates new block with tiles[x][y] as id number
				if(temp < numMovingBlocks){
					movingBlocks.add(new MovingBlock(x * Block.size, y * Block.size,
							tiles[x][y], x*Block.size+8*Block.size, (x*Block.size)+1*Block.size));
					blocks[x][y] = new Block(x*Block.size, y*Block.size, 0, ss);
				}else{
					blocks[x][y] = new Block(x * Block.size,
							y * Block.size, tiles[x][y], ss);
				}
			}
		}
		
	}
	
	public void tick(){
		for(int i = 0; i < movingBlocks.size(); i++){
			movingBlocks.get(i).tick();			
		}
	}
	
	public Block[][] getBlocks(){
		return blocks;
	}
	
	public ArrayList<MovingBlock> getMovingBlocks(){
		return movingBlocks;
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < blocks.length; i++){
			for(int j = 0; j < blocks[0].length; j++){
				blocks[i][j].draw(g);				
			}
		}
		
		for(int i = 0; i < movingBlocks.size(); i++){
			if(movingBlocks.get(i).getId() != 0){
				movingBlocks.get(i).draw(g);			
			}
		}
	}
	
}
