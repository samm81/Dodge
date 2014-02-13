package fowler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 @author Danny Fowler
 * 
 */
@SuppressWarnings("serial")
public class PongGameDone extends GameTemplate {

	// create your entities here
	private PaddleDone p1, p2;

	public static final int STATE_PLAYING = 1; // state values
	public static final int STATE_DONE = 2;

	private int gameState; // current state

	public static final Color BG_COL = new Color(100, 0, 0); // background
	public static final Color WALL_COL = new Color(0, 80, 200); // walls

	public static final int WALL_SIZE = 3; // wall size

	public static final Font SCORE_FONT = new Font("Lucida Console", Font.BOLD
			& Font.ITALIC, 20);

	// Load background images
	private static BufferedImage IMAGE_BG = null;
	{
		try {
			IMAGE_BG = ImageIO.read(new File("bluecircle.png"));
		} catch (IOException ex) {

		}
	}

	/**
	 * Create Frame and Panel
	 */
	public static void main(String[] args) {

		PongGameDone game = new PongGameDone();
		createGameFrame(game, 600, 400);
		game.init();
	}

	/**
	 * Create a Pong game panel
	 */
	public PongGameDone() {
		super();
	}

	/**
	 * Initialize game - insert code to set up (not create!) entities
	 */
	public void init() {
		p1 = new PaddleDone(20, 100);
		p2 = new PaddleDone(20, 100);
		start();
	}

	/**
	 * (re)start game - reset positions, scores etc
	 */
	public void start() {
		gameState = STATE_PLAYING;
		p1.setSpeed(5);
		p1.setTopX(20); // reset positions
		p1.setTopY(getHeight() / 2 - p1.getSizeY() / 2);

		p2.setSpeed(5);
		p2.setTopX(getWidth() - 40); // reset positions
		p2.setTopY(getHeight() / 2 - p1.getSizeY() / 2);
	}

	/**
	 * Update the screen - draw the environment, and then call the draw()
	 * methods for each sprite
	 * 
	 * @param g
	 *            graphics object
	 */
	public void updateFrame(Graphics2D g) {

		switch (gameState) {
		case STATE_PLAYING:

			if (isAKeyDown(KeyEvent.VK_Q) && canMove(p1, true))
				p1.doMove(true);
			else if (isAKeyDown(KeyEvent.VK_A) && canMove(p1, false))
				p1.doMove(false);

			if (isAKeyDown(KeyEvent.VK_UP) && canMove(p2, true))
				p2.doMove(true);
			else if (isAKeyDown(KeyEvent.VK_DOWN) && canMove(p2, false))
				p2.doMove(false);

			drawBoard(g);
			drawSprites(g);
			break;

		case STATE_DONE:
			g.drawString("Play again? Press Y or N", 200, 100);
			// here we should check for keys y or n
			break;

		}

	}

	/**
	 * Test whether a player can move up or down
	 * 
	 * @param p
	 *            player to test
	 * @param moveUpOrDown
	 *            trying to move up or down
	 */
	public boolean canMove(PaddleDone p, boolean moveUpOrDown) {
		if (moveUpOrDown) {
			if (p.getTopY() <= WALL_SIZE + p.getSpeed())
				return false;
			else
				return true;
		}
		if (p.getTopY() + p.getSizeY() >= getHeight() - WALL_SIZE
				- p.getSpeed())
			return false;
		else
			return true;

	}

	/**
	 * Stuff to draw when we're playing
	 * 
	 * @param g
	 */
	private void drawSprites(Graphics2D g) {

		p1.draw(g);
		p2.draw(g);
	}

	/**
	 * Draw the Pong board background
	 */
	private void drawBoard(Graphics2D g) {
		g.setColor(BG_COL);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(IMAGE_BG, 0, 0, null);
		// draw top and bottom walls
		g.setColor(WALL_COL);
		g.fillRect(0, 0, getWidth(), WALL_SIZE);
		g.fillRect(0, getHeight() - WALL_SIZE, getWidth(), WALL_SIZE);

	}

}