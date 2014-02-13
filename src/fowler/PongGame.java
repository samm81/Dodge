package fowler;
import java.awt.Color;
import java.awt.Graphics2D;
/**
 * 
	@author Danny Fowler 
 *
 */
@SuppressWarnings("serial")
public class PongGame extends GameTemplate  {

	//create your entities here 
	private Paddle p1,p2;
	
	public static final int STATE_PLAYING=1; //state values
	public static final int STATE_DONE=2;
	
	public static final Color BG_COL=new Color(100,0,0); //background
	public static final Color WALL_COL=Color.white; //walls
	
	public static final int WALL_SIZE=3; //wall size
	 
	 
	/**
	 * Create Frame and Panel
	 */
	public static void main(String[] args){
		 
		PongGame game=new PongGame();
		createGameFrame(game, 600, 400);
		game.init();
	}

	/**
	 * Create a Pong game panel
	 */
	public PongGame(){
		super();		
	} 
	/**
	 * Initialize game - insert code to set up (not create!) entities
	 */
	public void init(){
		p1=new Paddle(20,100);
		p2=new Paddle(20,100);
		start();
	}
	 
	/**
	 * (re)start game - reset positions, scores etc
	 */
	public void start(){
		p1.setSpeed(5);
		p1.setTopX(20); //reset positions
		p1.setTopY(getHeight()/2-p1.getSizeY()/2);
		
		p2.setSpeed(5);
		p2.setTopX(getWidth()-40); //reset positions
		p2.setTopY(getHeight()/2-p1.getSizeY()/2);
	}
	
/**
 * Update the screen - draw the environment, and then call the draw() methods for each sprite
 * @param g graphics object 
 */
	public void updateFrame(Graphics2D g){ 
		drawBoard(g);
		drawSprites(g);
	}

	/**
	 * Stuff to draw when we're playing
	 * @param g
	 */
	private void drawSprites(Graphics2D g){
		 
		p1.draw(g);
		p2.draw(g);
	}
	
	
	/**
	 * Draw the Pong board background
	 */
	private  void drawBoard(Graphics2D g){
		g.setColor(BG_COL); 
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//draw top and bottom walls
		g.setColor(WALL_COL); 
		g.fillRect(0, 0, getWidth(), WALL_SIZE);
		g.fillRect(0, getHeight()-WALL_SIZE, getWidth(), WALL_SIZE);
		 
		
	}
	
	

}