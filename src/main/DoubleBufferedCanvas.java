package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * 
 * @author Sam Maynard
 * @description Abstract class to deal with making a Double Buffered Canvas.
 * 
 */
@SuppressWarnings("serial")
abstract class DoubleBufferedCanvas extends Canvas implements Runnable {

	protected Thread thread;

	protected int fps;
	private int pauseTime;

	protected Image buffer;
	protected int bufferWidth;
	protected int bufferHeight;
	protected Graphics bufferGraphics;

	protected HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();

	private FPSCounter fpsCounter;

	private boolean initialized = false;

	/**
	 * constructor.
	 * 
	 * @param fps the frames per second for which the canvas is to run at
	 */
	public DoubleBufferedCanvas(int fps) {
		super();
		this.fps = fps;
		if (fps == 0)
			this.pauseTime = 0;
		else
			this.pauseTime = (int) (1000f / (float) fps);

		fpsCounter = new FPSCounter();

		addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				keys.put(key, true);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				keys.put(key, false);
			}

		});

		thread = new Thread(this);
	}

	public boolean keyDown(int keyEvent) {
		return keys.get(keyEvent) != null && keys.get(keyEvent) == true;
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * allows variables to be initialized
	 */
	abstract void init();

	@Override
	public void paint(Graphics g) {
		if(!initialized){
			init();
			initialized = true;
		}

		int width = this.getWidth();
		int height = this.getHeight();

		if (buffer == null || bufferGraphics == null
				|| width != bufferWidth
				|| height != bufferHeight)
			resetBuffer();

		bufferGraphics.clearRect(0, 0, bufferWidth, bufferHeight);
		draw(bufferGraphics);
		fpsCounter.paintSelf(bufferGraphics, width - 40, 30);
		g.drawImage(buffer, 0, 0, null);
	}

	/**
	 * resets the buffer
	 */
	private void resetBuffer() {
		bufferWidth = this.getWidth();
		bufferHeight = this.getHeight();

		if (bufferGraphics != null) {
			bufferGraphics.dispose();
			bufferGraphics = null;
		}
		if (buffer != null) {
			buffer.flush();
			buffer = null;
		}
		System.gc();

		buffer = createImage(bufferWidth, bufferHeight);
		bufferGraphics = buffer.getGraphics();
	}

	/**
	 * actually draws the image
	 * 
	 * @param g graphics to draw with
	 */
	abstract void draw(Graphics g);

	@Override
	public void run() {
		while (Thread.currentThread() == thread) {
			if(initialized){
				updateVars();
				repaint();
				if (pauseTime > 0) {
					try {
						Thread.sleep(pauseTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * starts the canvas animation
	 */
	public void start() {
		thread.start();
	}

	/**
	 * for any global variable updating that may need to be done
	 */
	abstract protected void updateVars();

	private class FPSCounter {

		long lastTime;

		int f = 0;
		
		long lastUpdate;
		float updatesPerSecond = 5f;
		float updateTime = 1000f / updatesPerSecond;

		public FPSCounter() {
			lastTime = System.currentTimeMillis();
			lastUpdate = lastTime;
		}

		public void paintSelf(Graphics g, int x, int y) {
			tick();

			g.setFont(new Font("Courier New", Font.BOLD, 26));
			g.setColor(Color.RED);
			g.drawString("" + f, x, y);
		}

		private void tick() {
			long time = System.currentTimeMillis();

			long diff = time - lastUpdate;
			if(diff > updateTime){
				diff = time - lastTime;
				f = (int) (1000f / diff);
				lastUpdate = time;
			}

			lastTime = time;
		}

	}

}
