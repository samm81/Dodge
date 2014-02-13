package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

@SuppressWarnings("serial")
public class Dodge extends DoubleBufferedCanvas {

	Ball player;
	Ball[] enemies = new Ball[10];

	double playerSpeed = .3;

	int playerScore = 0;

	boolean gameStart = false;
	boolean gameOver = false;

	int width;
	int height;

	double G = 6.67428 * Math.pow(10, 1.5);
	
	int level = 1;

	public static void main(String[] args) {
		DoubleBufferedCanvas dodge = new Dodge(30);
		@SuppressWarnings("unused")
		Framer framer = new Framer("Dodge", 100, 100, 500, 500, dodge);
	}

	public Dodge(int fps) {
		super(fps);
	}

	@Override
	void init() {
		width = this.getWidth();
		height = this.getHeight();

		player = new Ball(new Vector(width / 2d, height / 2d), 16, 10, randColor());
		Random r = new Random();
		for (int i = 0; i < enemies.length; i++) {
			double x;
			double y;
			do {
				x = r.nextDouble() * width;
				y = r.nextDouble() * height;
			} while (!(x < 10 || x > (width - 10) || y < 10 || y > (height - 10)));
			enemies[i] = new Ball(new Vector(x, y), 10, 1, randColor());
		}

		playerScore = 0;
		level = 1;

	}

	private Color randColor() {
		Random r = new Random();
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}

	@Override
	void draw(Graphics g) {
		if (!gameStart && !gameOver) {
			g.setColor(new Color(255, 0, 0));
			g.drawString("SPACE TO START", width / 2 - 100, 30);
		}

		Graphics2D g2 = (Graphics2D) g;
		player.drawSelf(g2);
		for (Ball enemy : enemies) {
			enemy.drawSelf(g2);
			// enemy.drawVelocity(g2);
			// enemy.drawAcceleration(g2);
		}

		g.setColor(new Color(255, 0, 255));
		g.drawString("SCORE", 10, 30);
		g.drawString("" + playerScore, 10, 55);

		g.setColor(new Color(255, 255, 255));
		g.drawString("LEVEL: " + level, 10, height - 15);

		if (gameOver) {
			g.setColor(new Color(0, 255, 0));
			g.drawString("GAME OVER", width / 2 - 72, 30);
			g.drawString("Q TO QUIT", width / 2 - 72, 55);
			g.drawString("R TO RESTART", width / 2 - 96, 80);
		}
	}

	int w = KeyEvent.VK_W;
	int a = KeyEvent.VK_A;
	int s = KeyEvent.VK_S;
	int d = KeyEvent.VK_D;
	int space = KeyEvent.VK_SPACE;

	int r = KeyEvent.VK_R;
	int q = KeyEvent.VK_Q;
	
	@Override
	protected void updateVars() {
		if (keyDown(q))
			System.exit(0);
		if (gameOver && keyDown(r)) {
			init();
			gameOver = false;
		}
		if (keyDown(space))
			gameStart = true;

		if (gameStart) {
			// player.setVelocity(new Vector(0,0));
			if (keyDown(w))
				player.addVelocity(new Vector(0, -playerSpeed));
			if (keyDown(s))
				player.addVelocity(new Vector(0, playerSpeed));
			if (keyDown(a))
				player.addVelocity(new Vector(-playerSpeed, 0));
			if (keyDown(d))
				player.addVelocity(new Vector(playerSpeed, 0));
			
			double frictionConstant = 0.0003;
			double velocityX = player.getVelocity().getX();
			double velocityY = player.getVelocity().getY();
			Vector friction = new Vector(-1 * velocityX * frictionConstant,
					-1 * velocityY * frictionConstant);

			player.addAcceleration(friction);
			
			player.tick();
			player.checkInBounds(width, height);

			for (Ball enemy : enemies) {
				enemy.setAcceleration(new Vector(0, 0));
				Vector distance = enemy.getDistanceVector(player);
				double M = player.getMass();
				double m = enemy.getMass();
				double force = G * M * m / Math.pow(distance.getLength(), 2);

				double cap = .5;
				if (force > cap)
					force = cap;

				double angle = distance.getAngle();
				double forceX = force * Math.cos(angle);
				double forceY = force * Math.sin(angle);
				double accelerationX = (double) (forceX / m);
				double accelerationY = (double) (forceY / m);

				if (distance.getX() < 0) {
					accelerationX *= -1;
					accelerationY *= -1;
				}

				Vector acceleration = new Vector(accelerationX, accelerationY);

				enemy.addAcceleration(acceleration);

				// friction
				frictionConstant = 0.03;
				velocityX = enemy.getVelocity().getX();
				velocityY = enemy.getVelocity().getY();
				friction = new Vector(-1 * velocityX * frictionConstant,
						-1 * velocityY * frictionConstant);

				enemy.addAcceleration(friction);

				enemy.tick();
				enemy.checkInBounds(width, height);
			}

			for (Ball enemy : enemies) {
				if (player.circle.intersects(enemy.circle.getBounds2D())) {
					gameOver = true;
					gameStart = false;
				}
			}

			playerScore++;
			if (playerScore % 300 == 0){
				level = level + 1;
				player.mass *= 2;
			}

		}
	}

}
