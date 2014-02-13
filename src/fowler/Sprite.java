package fowler;
import java.awt.Graphics2D;

/**
 * Anything that moves on screen
 * @author fowlerda
 *
 */
public abstract class Sprite {
	private int _topX,_topY; //position on screen
	private int _sizeX,_sizeY; //size on screen
	
	/**
	 * Draw yourself
	 * @param g graphics object
	 */
	public abstract void draw(Graphics2D g);

	/**
	 * @return x coord of top left corner
	 */
	public int getTopX() {
		return _topX;
	}

	/**
	 * @param topX x coord of top left corner
	 */
	public void setTopX(int topX) {
		_topX = topX;
	}

	/**
	 * @return y coord of top left corner
	 */
	public int getTopY() {
		return _topY;
	}

	/**
	 * @param  topY y coord of top left corner
	 */
	public void setTopY(int topY) {
		_topY = topY;
	}

	/**
	 * 
	 * @return size in x dimension
	 */
	public int getSizeX() {
		return _sizeX;
	}
	
	/**
	 * 
	 * @param xSize size in x dimension
	 */
	public void setSizeX(int xSize) {
		_sizeX = xSize;
	}

	/**
	 * 
	 * @return size in y dimension
	 */
	public int getSizeY() {
		return _sizeY;
	}


	/**
	 * 
	 * @param ySize size in y dimension
	 */
	public void setSizeY(int ySize) {
		_sizeY = ySize;
	}
	
	
}
