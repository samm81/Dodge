package fowler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage; //image 
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO; //loader
/**
 * A Paddle
 * @author fowlerda
 *
 */
public class PaddleDone extends Sprite{
	private int _speedY=0; 
	 
	public static final Color FILL_COLOR=Color.white;
	public static  BufferedImage BAT;
	
	{
		try {
			BAT=ImageIO.read(new File("bat.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Create a paddle 
	 */
	public PaddleDone(int sizeX,int sizeY){
		//set size of paddle from image size 
		setSizeY(sizeY);
		setSizeX(sizeX); 
	}
	
	/**
	 * Draw the paddle on the screen
	 */
	public void draw(Graphics2D g){
		g.setColor(FILL_COLOR); //inside
		//draw the bat at xpos, ypos
		//g.fillRect(getTopX(), getTopY(), getSizeX(), getSizeY());
		g.drawImage(BAT,getTopX(),getTopY(),null);
		
	}
	
	/**
	 *
	 * @return  Speed to move with each frame
	 */
	public int getSpeed() {
		return _speedY;
	}
	
	/**
	 * @return  Speed to move with each frame
	 */
	public void setSpeed(int speed) {
		this._speedY = speed;
	}
	 
	 public void doMove(boolean moveUpOrDown){
		 
			if(moveUpOrDown==true) { //move up
				setTopY(getTopY()-_speedY);
			} else {
				setTopY(getTopY()+_speedY); //move down
			}
		 
	}
		
}
