package game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.GamePanel;
import game.main.ImageLoader;
import game.main.SpriteSheet;

public class OptionsState extends GameState{

	//private Color[] colors = {Color.red, Color.blue, Color.yellow, Color.green, 
	//new Color(255,100,0),new Color(255,0,255), Color.black, Color.white};
	
	private String[] icons = {"/res/Warrior1.png"};
	
	public static BufferedImage playerIcon, temp;

	public static SpriteSheet ss;
	
	public static Color playerColor;
	
	private static int index = 0;
	
	private static String confirmClick = "Once you have selected your player's color, press "
			+ "enter to return to the home screen.";
	
	public OptionsState(GameStateManager gsm, int levelPath, int backgroundPath){
		super(gsm, 0, 0);
		ss = new SpriteSheet(new ImageLoader().loadImage("/res/GameSheet1.png"));
	}
	
	public void init(int levelPath, int backgroundPath){
		
	}

	public void tick(){
		
	}

	public void draw(Graphics g){
		g.setColor(new Color(200,0,0));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g.setColor(Color.yellow);
		if(index >= 0){
			if(index < 4)
				g.drawRect(GamePanel.WIDTH / 3 - 30, 70 + index * 90, 40, 60);
			else
				g.fillRect( 2 * GamePanel.WIDTH / 3 - 30, index * 90 - 290, 40, 60);

			g.setFont(new Font("Arial", Font.PLAIN, 18));
			g.drawString(confirmClick, GamePanel.WIDTH / 8, 430);
		}
		
		for(int i = 0; i < icons.length; i++){			
			temp = ss.crop(24, 1, 32, 48);
			if(i < 4){
				g.drawImage(temp, GamePanel.WIDTH / 3 - 25, 75 + i * 90, null);
			}else{
				g.drawImage(temp, 2 * GamePanel.WIDTH / 3 - 25, i * 90 - 285, null);
			}
		}
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Choose your Player color", GamePanel.WIDTH / 3 - 15, 40);
		//g.drawLine(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT);
	}
	
	public void keyPressed(int k){
		if(k == KeyEvent.VK_ENTER){
			gsm.states.push(new MenuState(gsm, 0, 0));
		}
		
		if(k == KeyEvent.VK_DOWN){
			if(index >= icons.length - 1){
				
			}else
				index++;
		}
		
		if(k == KeyEvent.VK_UP){
			if(index <= 0){
				
			}else
				index--;
		}

		//playerColor = colors[index];
		
		try {
			playerIcon = ImageIO.read(this.getClass().getResourceAsStream(icons[index]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void keyReleased(int k){
		
	}

	public void mouseClicked(MouseEvent e){
		
	}

	public void mouseClicked(int k, int j){
		/*for(int i = 0; i < colors.length; i++){
			if(k >= GamePanel.WIDTH / 3 - 25 && k <= GamePanel.WIDTH / 3 + 25){
				if(j >= 75 + i * 90 && j <= 125 + i * 90){
					if(i == 0)
						index = 0;
					if(i == 1)
						index = 1;
					if(i == 2)
						index = 2;
					if(i == 3)
						index = 3;

					playerColor = colors[i];
				}
			}
			
			if(k >= 2 * GamePanel.WIDTH / 3 - 25 && k <= 2 * GamePanel.WIDTH / 3 + 25){
				if(j >= i * 90 - 285 && j <= i * 90 - 235){
					if(i == 4)
						index = 4;
					if(i == 5)
						index = 5;
					if(i == 6)
						index = 6;
					if(i == 7)
						index = 7;
					
					playerColor = colors[i];
				}
			}
		}*/
	}

}
