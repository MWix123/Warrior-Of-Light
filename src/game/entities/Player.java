package game.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.main.Game;
import game.main.GamePanel;
import game.main.SpriteSheet;
import game.objects.Blast;
import game.objects.Block;
import game.objects.MovingBlock;
import game.physics.Collision;
import game.states.GameState;
import game.states.LevelState;

public class Player extends Rectangle{//5:01

	private static final long serialVersionUID = 1L;
	private static double speed = 4;	
	
	private BufferedImage bImage, pImage;
	private BufferedImage playerPositions[];
	
	public static SpriteSheet ss;
	public Blast blast;
	
	private double x, y;
	private int width, height, direction, xPos, yPos, counterx = 0, timer = 0;
	
	public static int playerLife = 3;
	public static boolean gameOver = false, paused = false;
	
	public boolean right = false, left = false, jumping = false,
			falling = false, topCollision = false, shooting = false, 
			levelEnd = false, shootingEnabled = false;
	
	private static double jumpSpeed = 10.4, currentJumpSpeed = jumpSpeed, fallingSpeed = 0.1, 
			maxFallingSpeed = 10.4;//prev 5.2
	
	public Player(int width, int height, int xPos, int yPos, SpriteSheet ss){
		x = GamePanel.WIDTH / 2 - (width / 2);
		y = GamePanel.HEIGHT / 2 - (height / 2);
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		Player.ss = ss;
		
		init();
		
	}
	
	public void init(){
		direction = 1;
		pImage = ss.crop(0, 1, 32, 48);
		try{
			bImage = ImageIO.read(this.getClass().getResourceAsStream("/res/Life.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		playerPositions = new BufferedImage[20];
		
		playerPositions[0] = ss.crop(14, 1, 32, 48);
		playerPositions[1] = ss.crop(12, 1, 32, 48);
		playerPositions[2] = ss.crop(16, 1, 32, 48);
		playerPositions[3] = ss.crop(22, 1, 32, 48);
		playerPositions[4] = ss.crop(20, 1, 32, 48);
		playerPositions[5] = ss.crop(18, 1, 32, 48);		
		playerPositions[6] = ss.crop(24, 1, 32, 48);
		playerPositions[7] = ss.crop(26, 1, 32, 48);
		playerPositions[8] = ss.crop(28, 1, 32, 48);
		playerPositions[9] = ss.crop(30, 1, 32, 48);
		playerPositions[10] = ss.crop(32, 1, 32, 48);
		playerPositions[11] = ss.crop(34, 1, 32, 48);
		playerPositions[12] = ss.crop(36, 1, 32, 48);
		playerPositions[13] = ss.crop(48, 1, 32, 48);
		playerPositions[14] = ss.crop(46, 1, 32, 48);
		playerPositions[15] = ss.crop(44, 1, 32, 48);
		playerPositions[16] = ss.crop(42, 1, 32, 48);
		playerPositions[17] = ss.crop(40, 1, 32, 48);
		playerPositions[18] = ss.crop(38, 1, 32, 48);
		playerPositions[19] =  ss.crop(0, 1, 32, 48);
	}
	
	public void tick(Block[][] b, ArrayList<MovingBlock> movingBlocks){
		//The Point references the player's position
		if(!paused){
		
		int xO = (int)GameState.xOffset;
		int yO = (int)GameState.yOffset;
		for(int i = 0; i < b.length; i++){
			
			for(int j = 0; j < b[0].length; j++){
				if(b[i][j].id != 0 && b[i][j].id != 14){
					if(b[i][j].x + 32> GameState.xOffset && b[i][j].x < GameState.xOffset + GamePanel.WIDTH
							&& b[i][j].y + 32> GameState.yOffset && b[i][j].y < GameState.yOffset + GamePanel.HEIGHT){
						//right collision(left side of block)
						//getting rid of added numbers makes person slide
						if(Collision.playerBlock(new Point((int)x + width + xO + 1, 
								(int)y + yO + 10), b[i][j]) || Collision.playerBlock(
										new Point((int)x + width + xO + 1,
												(int)y + height + yO - 10), b[i][j])){
							//right = false;
							if(b[i][j].id == 4){
								levelEnd = true;
							}
							if(b[i][j].id == 7){
								shootingEnabled = true;
								b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
							}if(b[i][j].id == 10){
								detectHit(true,true);
							}
							GameState.xOffset -=speed;
						}
						
						//left collision(right side of block)
						if(Collision.playerBlock(new Point((int)x + xO - 1, 
								(int)y + yO + 10), b[i][j]) || Collision.playerBlock(
										new Point((int)x + xO - 1,
												(int)y + height + yO - 10), b[i][j])){
							//left = false;
							if(b[i][j].id == 4){
								levelEnd = true;
							}
							if(b[i][j].id == 7){
								shootingEnabled = true;
								b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
							}if(b[i][j].id == 10){
								detectHit(true,false);
							}
							GameState.xOffset +=speed;
						}
						
						//top collision(bottom of block)
						if(Collision.playerBlock(new Point((int)x + xO + 1, 
								(int)y + yO), b[i][j]) || Collision.playerBlock(
										new Point((int)x + width + xO - 1,
												(int)y + yO), b[i][j])){
							jumping = false;
							falling = true;
							if(b[i][j].id == 5){
								b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
								b[i][j].blockNoise();
							}
							if(b[i][j].id == 4){
								levelEnd = true;
							}
							if(b[i][j].id == 3){
								b[i][j] = new Block(i * Block.size, j * Block.size, 6,ss);
								b[i][j].blockNoise();
								j -= 1;
								b[i][j] = new Block(i * Block.size, j * Block.size, 7,ss);
							}
						}
						
						//bottom collision(top of block)
						if(Collision.playerBlock(new Point((int)x + xO + 2, 
								(int)y + height + yO + 1), b[i][j]) || Collision.playerBlock(
										new Point((int)x + width + xO - 1,
												(int)y + height + yO + 2), b[i][j])){
							y = (b[i][j].getY() - height - GameState.yOffset);
							if(b[i][j].id == 9 && falling){
								playerLife--;
								resetPlayer();
							}
							falling = false;
							if(jumping == false){
								currentJumpSpeed = jumpSpeed;
							}
							topCollision = true;
							if(b[i][j].id == 4){
								levelEnd = true;
							}
							if(b[i][j].id == 7){
								shootingEnabled = true;
								b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
							}
							if(b[i][j].id == 10){
								b[i][j] = new Block(j * Block.size, i * Block.size, 0,ss);
							}
						}else{
							if(!topCollision && !jumping)
								falling = true;
						}
					}
				}
			}
			if(!(i >= movingBlocks.size())){
				if(movingBlocks.get(i).getId() != 0){
					if(Collision.playerMovingBlock(new Point((int)x + width + xO + 1, 
							(int)y + yO + 10), movingBlocks.get(i)) || Collision.playerMovingBlock(
									new Point((int)x + width + xO + 1,
											(int)y + height + yO - 10), movingBlocks.get(i))){
						if(movingBlocks.get(i).getId() == 1){
							detectHit(true,true);
						}
						GameState.xOffset -=speed;
					}
					
					//left collision(right side of block)
					if(Collision.playerMovingBlock(new Point((int)x + xO - 1, 
							(int)y + yO + 10), movingBlocks.get(i)) || Collision.playerMovingBlock(
									new Point((int)x + xO - 1,
											(int)y + height + yO - 10), movingBlocks.get(i))){
						if(movingBlocks.get(i).getId() == 1){
							detectHit(true,false);
						}
							GameState.xOffset +=speed;
					}
						
					//top collision(bottom of block)
					if(Collision.playerMovingBlock(new Point((int)x + xO + 1, 
							(int)y + yO), movingBlocks.get(i)) || Collision.playerMovingBlock(
									new Point((int)x + width + xO - 1,
											(int)y + yO), movingBlocks.get(i))){
						jumping = false;
						falling = true;
						if(movingBlocks.get(i).getId() == 5){
							movingBlocks.remove(i);
						}
					}
						
					//bottom collision(top of block)
					if(Collision.playerMovingBlock(new Point((int)x + xO + 2, 
							(int)y + height + yO + 1), movingBlocks.get(i)) || Collision.playerMovingBlock(
									new Point((int)x + width + xO - 1,
											(int)y + height + yO + 2), movingBlocks.get(i))){
						//y = movingBlocks.get(i).getY() - height - (int) GameState.yOffset;
						falling = false;
						topCollision = true;
						
						GameState.xOffset += movingBlocks.get(i).getMove();
						
						if(movingBlocks.get(i).getId() == 1){
							movingBlocks.remove(i);
						}
					}else{
						if(!topCollision && !jumping)
							falling = true;
					}
				}	
			}
		}
		
		//detect player falling to death
		if(y >= GamePanel.HEIGHT){
			playerLife--;
			resetPlayer();			
		}
		
		topCollision = false;
		
		//movement
		if(right && x <= GamePanel.WIDTH - width){
			GameState.xOffset+=speed;
			direction = 1;
			if(!jumping){
				counterx+=speed;
				if(shootingEnabled){
					direction = 8;
					if(counterx >= 30){
						direction = 9;
						if(counterx >= 60){
							direction = 8;
							if(counterx >= 90){
								direction = 10;
								if(counterx >= 120){
									counterx = 0;
								}
							}
						}				
					}
				}else{
					if(counterx >= 30){
						direction = 2;
						if(counterx >= 60){
							direction = 1;
							if(counterx >= 90){
								direction = 3;
								if(counterx >= 120){
									counterx = 0;
								}
							}
						}				
					}
				}
				changeImage(direction);
			}else{
				if(shootingEnabled){
					changeImage(9);
				}else{
					changeImage(1);
				}
			}
		}
		if(left && x >= 0){
			GameState.xOffset-=speed;
			direction = 4;
			if(!jumping){
				counterx+=speed;
				if(shootingEnabled){
					direction = 14;
					if(counterx >= 30){
						direction = 15;
						if(counterx >= 60){
							direction = 14;
							if(counterx >= 90){
								direction = 16;
								if(counterx >= 120){
									counterx = 0;
								}
							}
						}				
					}
				}else{
					if(counterx >= 30){
						direction = 5;
						if(counterx >= 60){
							direction = 4;
							if(counterx >= 90){
								direction = 6;
								if(counterx >= 120){
									counterx = 0;
								}
							}
						}				
					}
				}
				changeImage(direction);
			}
			else{
				if(shootingEnabled){
					direction = 15;
					changeImage(direction);
				}else{
					changeImage(5);
				}
			}
		}
		
		if(jumping){
			//y-=currentJumpSpeed;
			GameState.yOffset -= currentJumpSpeed;
			if(currentJumpSpeed >= 5.2){
				currentJumpSpeed -= 0.4;//prev 0.2
			}else{
				currentJumpSpeed -= 0.2;
			}
			if(currentJumpSpeed <= 0 || y <= 0){
				currentJumpSpeed = jumpSpeed;
				jumping = false;
				falling = true;
			}
		}
		
		if(!(right || left)){
			if(shootingEnabled){
				changeImage(7);
			}else{
				changeImage(0);
			}
		}
		
		if(falling){
			GameState.yOffset += fallingSpeed;
			timer++;
			if(timer >= 120){
				y+=fallingSpeed;
				GameState.yOffset -= fallingSpeed;
			}
			if(fallingSpeed < maxFallingSpeed){
				fallingSpeed += 0.2;
			}			
		}
		
		if(!falling){
			double tempY = y;
			y = (GamePanel.HEIGHT/2) - (height / 2);
			GameState.yOffset -= (y - tempY);
			fallingSpeed = 0.1;
			timer = 0;
			
		}
		
		//Blasting
		if(shooting){
			try{
			if(blast.isShooting(shooting)){
				blast.tick(speed, b, movingBlocks);
				if(right){
					direction = 11;
					if(counterx >= 30){
						direction = 12;
						if(counterx >= 60){
							direction = 11;
							if(counterx >= 90){
								direction = 13;
								if(counterx >= 120){
									counterx = 0;
								}
							}
						}				
					}
				}else if(left){
					direction = 17;
					if(counterx >= 30){
						direction = 18;
						if(counterx >= 60){
							direction = 17;
							if(counterx >= 90){
								direction = 19;
								if(counterx >= 120){
									counterx = 0;
								}
							}
						}				
					}
				}
				if(!(right || left)){
					direction = 11;
				}
				changeImage(direction);
			}else{
				shooting = false;
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		}
		
		//System.out.println("main x offset: " + xPos + ", y offset: " + yPos + " game state x: " + GameState.xOffset + ", y: " + GameState.yOffset);		
		
	}

	public void draw(Graphics g){
		if(pImage != null){
			g.drawImage(pImage, (int)x, (int)y, width, height, null);
		}else{
			g.fillRect((int)x, (int)y, width, height);
		}
		//g.drawLine(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT);
		
		for(int i = 0; i < playerLife; i++){
			g.drawImage(bImage, 20 + 50 * i, 20, null);
		}
		
		if(shooting && blast != null){
			blast.draw(g);
		}
		
	}

	public void keyPressed(int k){
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT){
			right = true;			
		}
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT){
			left = true;
		}
		if(k == KeyEvent.VK_SPACE && !jumping && !falling && !paused){
			jumping = true;
		}
		
		if(k == KeyEvent.VK_Z && !shooting && shootingEnabled){
			shooting = true;
			blast = new Blast();
			blast.init((int)x + width - 4,(int)y + (height / 2) + 2,direction,ss);
		}
		
		if(k == KeyEvent.VK_ENTER){
			if(!paused){
				paused = true;
			}else if(!LevelState.pauseState){
				paused = false;
			}
		}
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT){
			right = false;
		}
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT){
			left = false;
		}
	}
	
	public void changeImage(int position){
		switch(position){
		    case 1:
		        pImage = playerPositions[0];
		        break;
		    case 2:
		        pImage = playerPositions[1];
		        break;
		    case 3:
		        pImage = playerPositions[2];
		        break;
		    case 4:
		        pImage = playerPositions[3];
		        break;
		    case 5:
		        pImage = playerPositions[4];
		        break;
		    case 6:
		        pImage = playerPositions[5];
		        break;
		    case 7:
		        pImage = playerPositions[6];
		        break;
		    case 8:
		        pImage = playerPositions[7];
		        break;
		    case 9:
		        pImage = playerPositions[8];
		        break;
		    case 10:
		        pImage = playerPositions[9];
		        break;
		    case 11:
		        pImage = playerPositions[10];
		        break;
		    case 12:
		        pImage = playerPositions[11];
		        break;
		    case 13:
		        pImage = playerPositions[12];
		        break;
		    case 14:
		        pImage = playerPositions[13];
		        break;
		    case 15:
		        pImage = playerPositions[14];
		        break;
		    case 16:
		        pImage = playerPositions[15];
		        break;
		    case 17:
		        pImage = playerPositions[16];
		        break;
		    case 18:
		        pImage = playerPositions[17];
		        break;
		    case 19:
		        pImage = playerPositions[18];
		        break;
		    default:
		        pImage = playerPositions[19];
		        break;
		}
			
	}
	
	public void resetPlayer(){
		x = GamePanel.WIDTH / 2 - (width / 2);
		y = GamePanel.HEIGHT / 2 - (height / 2);
		GameState.xOffset = xPos;
		GameState.yOffset = yPos;
		
		falling = false;
		detectHit(false,false);
	}
	
	public void detectHit(boolean enemy, boolean dir){
		if(enemy){
			right = false;
			left = false;
			if(!shootingEnabled){
				playerLife--;
			}
			if(dir)
				GameState.xOffset -= 15;
			else
				GameState.xOffset += 15;
		}
		
		shootingEnabled = false;
		
		if(playerLife <= 0){
			gameOver = true;
			speed = 4;
		}
		changeImage(1);
	}
}
