package game.mapping;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import game.main.SpriteSheet;
import game.objects.Block;
import game.objects.MovingBlock;

public class Map {

	private String path, line;
	private int width, height;
	private ArrayList<MovingBlock> movingBlocks;
	
	private Block blocks[][]; //multi-dim array that creates a grid for blocks
	private SpriteSheet ss;
	
	public Map(String loadString, SpriteSheet ss){
		path = loadString;
		this.ss = ss;
		
		loadMap();		
	}
	
	public void tick(){
		for(int i = 0; i < movingBlocks.size(); i++){
			movingBlocks.get(i).tick();			
		}
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
	
	public void loadMap(){
		InputStream is = this.getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		try {
			width = Integer.parseInt(br.readLine()); //reads first line of file
			height = Integer.parseInt(br.readLine());
			
			blocks = new Block[height][width]; //height is column length, width is row length
						
			for(int y = 0; y < height; y++){
				line = br.readLine();
				String[] tokens = line.split("\\s+"); // reads the remaining lines
				for(int x = 0; x < width; x++){
					blocks[y][x] = new Block(x * Block.size, y * Block.size, Integer.parseInt(tokens[x]), ss);
				}
			}
			
			line = br.readLine();
			line = br.readLine();
			int length = Integer.parseInt(line);
			movingBlocks = new ArrayList<MovingBlock>();
			
			for(int i = 0; i < length; i++){
				line = br.readLine();
				String[] tokens = line.split("\\s+");
				
				movingBlocks.add(new MovingBlock(Integer.parseInt(tokens[0])* Block.size, 
						Integer.parseInt(tokens[1]) * Block.size, Integer.parseInt(tokens[2]), 
						Integer.parseInt(tokens[3]) * Block.size, Integer.parseInt(tokens[4]) * Block.size));
			}
			
			
		} catch (NumberFormatException | IOException e){
			e.printStackTrace();
		}
	}
	
	public Block[][] getBlocks(){
		return blocks;
	}
	
	public ArrayList<MovingBlock> getMovingBlocks(){
		return movingBlocks;
	}
}
