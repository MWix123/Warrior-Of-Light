package game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.entities.Player;
import game.states.GameState;

public class MovingBlock extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	private int leftBound, rightBound, move, xSize, ySize;
	private int id;
	
	private BufferedImage blockImage;
	
	public MovingBlock(int x, int y, int id, int leftBound, int rightBound){
		this.id = id;
		this.rightBound = rightBound;
		this.leftBound = leftBound;
		
		xSize = Block.size;
		ySize = Block.size;
		
		move = 1;
		
		switch(id){		
		case 1: //enemy type 1
			blockImage = Player.ss.crop(17, 0, 16, 16);
			xSize = Block.size*2;
			ySize = Block.size*2;
			break;
		case 2: //moving block right
			blockImage = Player.ss.crop(19, 0, 32, 16);
			xSize = Block.size*2;
			break;
		case 3: //moving block left
			blockImage = Player.ss.crop(19, 0, 32, 16);
			x = leftBound;
			move = -1;
			xSize = Block.size*2;
			break;
		default:
			break;			
		}
		

		setBounds(x, y, xSize, ySize);
	}
	
	public void tick(){
		if(x + width >= rightBound ){
			move *= -1;
		}
		if(x <= leftBound ){
			move *= -1;
		}
		
		x += move;
	}
	
	public void draw(Graphics g){
		if(id != 0){
			g.drawImage(blockImage, x - (int)GameState.xOffset, y - (int)GameState.yOffset, width, height,null);
		}
	}
	
	public int getMove(){
		return move;
	}
	
	public int getId(){
		return id;
	}
}
