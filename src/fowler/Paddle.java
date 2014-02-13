package fowler;
import java.awt.Color;
import java.awt.Graphics2D;
//image 
//loader
/**
 * A Paddle
 * @author fowlerda
 *
 */
public class Paddle extends Sprite{
	private int _speedY=0; 
	 
	public static final Color FILL_COLOR=Color.white;
	/**
	 * Create a paddle 
	 */
	public Paddle(int sizeX,int sizeY){
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
		g.fillRect(getTopX(), getTopY(), getSizeX(), getSizeY());
		
		
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
	 
}
