package fowler;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * A template for a simple Java game.
 * To create a game, extend this class
 * --Put initialization code in init(), and game reset code in start()
 * --Put update and screen painting code in updateFrame()
 * --Create a main method that creates a game object, calls createGameFrame(), and then calls init()
	@author Danny Fowler 
 *
 */
@SuppressWarnings("serial")
public abstract class GameTemplate extends Canvas implements Runnable, KeyListener {

	//create your entities here
	private int[] keysDown=new int[0];



	//for double buffered graphics
	private int bufferWidth;
	private int bufferHeight;
	private Image bufferImage;
	private Graphics bufferGraphics; 


	/**
	 * Initialize game - override this method to set up (not instantiate!) game entities
	 *This method should call start()
	 */
	public abstract void init();

	/**
	 * (re)start game - reset positions, scores etc
	 */
	public abstract void start();

	/**
	 * Update the screen - draw the environment, and then call the draw() methods for each sprite
	 * @param g graphics object 
	 */
	public abstract void updateFrame(Graphics2D g);

	/**
	 * Test whether a key is currently being pressed down 
	 * Multiple keys can be pressed simultaneously
	 * @param keyToTest determine if this key is down (see VK_ constants in java.awt.event.KeyEvent)
	 * @return true if this keys is down otherwise false
	 */
	public boolean isAKeyDown(int keyToTest){
		for (int key : keysDown) {
			if (key==keyToTest) return true;
		}
		return false;
	}
	/**
	 * Triggered when a key is released (up)
	 * @param keyE which key
	 */
	public void keyReleased(KeyEvent keyE) {
		// Remove key released
		int[] newDown=new int[keysDown.length-1];
		int atLoc=0;
		for (int i = 0; i < keysDown.length; i++) {
			if(keysDown[i]!=keyE.getKeyCode()){
				newDown[atLoc]=keysDown[i];
				atLoc++;
			}
		}
		keysDown=newDown;
	}

	/**
	 * Triggered when a key is pressed (up)
	 * @param keyE which key
	 */

	public void keyPressed(KeyEvent keyE) {
		boolean add=true;
		int[] newDown=new int[keysDown.length+1];
		for (int i = 0; i < keysDown.length; i++) {
			if(keysDown[i]==keyE.getKeyCode())	{add=false;break;}
			newDown[i]=keysDown[i];
		}

		if(add){

			newDown[keysDown.length]=keyE.getKeyCode();
			keysDown=newDown;
		}
	}


	/**
	 * Template code follows
	 */
	private Thread thisThread;

	public GameTemplate(){
		super();		
		thisThread=new Thread(this); //create a thread for an object
		thisThread.start(); 
	}

	/**
	 * Start the thread
	 */
	public void run(){

		while(Thread.currentThread()== thisThread){ //am I running?
			repaint(); //redraw screen
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Double buffered update
	 */
	public void update(Graphics g){
		paint(g);
	}


	public void paint(Graphics g){
		//    checks the buffersize with the current panelsize
		//    or initialises the image with the first paint
		if(bufferWidth!=getSize().width || 
				bufferHeight!=getSize().height || 
				bufferImage==null || bufferGraphics==null)
			resetBuffer();

		if(bufferGraphics!=null){
			//this clears the offscreen image, not the onscreen one
			bufferGraphics.clearRect(0,0,bufferWidth,bufferHeight);

			//calls the paintbuffer method with 
			//the offscreen graphics as a param
			updateFrame((Graphics2D)bufferGraphics);

			//we finaly paint the offscreen image onto the onscreen image
			g.drawImage(bufferImage,0,0,this);
		}
	}

	/** 
	 * Reinitialize double buffered graphics when canvas changes size
	 */
	private void resetBuffer(){
		// always keep track of the image size
		bufferWidth=getSize().width;
		bufferHeight=getSize().height;

		//    clean up the previous image
		if(bufferGraphics!=null){
			bufferGraphics.dispose();
			bufferGraphics=null;
		}
		if(bufferImage!=null){
			bufferImage.flush();
			bufferImage=null;
		}
		System.gc();

		//    create the new image with the size of the panel
		bufferImage=createImage(bufferWidth,bufferHeight);
		bufferGraphics=bufferImage.getGraphics();
	}

	/**
	 * Create Frame and Panel
	 * Call this method to start the game
	 * @param game canvas
	 * @param width width of game frame
	 * @param height height of game frame
	 */
	public static void createGameFrame(GameTemplate game, int width, int height){
		Frame myFrame=new Frame(); 

		myFrame.setSize(  width,height); //frame size
		myFrame.setBackground(Color.white);

		myFrame.add(game);
		game.addKeyListener(game);
		//Make sure program ends when window is closed
		WindowAdapter d=new WindowAdapter() {
			public void windowClosing(WindowEvent e){

				System.exit(0);
			}

		};



		myFrame.addWindowListener(d);
		myFrame.setVisible(true); //see frame
		game.requestFocus(); //make sure the game is selected


	}

	//ignore this method
	public void keyTyped(KeyEvent e) {

	}


}