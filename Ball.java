package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ball {

	protected Vector position;
	protected Vector velocity;
	protected Vector acceleration;

	protected double width;
	protected double mass;

	protected Color color;

	Ellipse2D.Double circle;

	public Ball(Vector position, double radius, double mass, Color color) {
		this.position = position;
		this.width = radius;
		circle = new Ellipse2D.Double(getX(), getY(), width, width);
		this.mass = mass;
		this.color = color;
		velocity = new Vector(0, 0);
		acceleration = new Vector(0, 0);
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

	public Vector getPosition() {
		return position;
	}

	public double getMass() {
		return mass;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public void addVelocity(Vector adjustment) {
		velocity = velocity.addVector(adjustment);
	}

	public void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}

	public void addAcceleration(Vector adjustment) {
		acceleration = acceleration.addVector(adjustment);
	}

	public Vector getVelocity() {
		return velocity;
	}

	public Vector getDistanceVector(Ball ball) {
		return ball.getPosition().subtractVector(this.getPosition());
	}

	public void drawSelf(Graphics2D g) {
		g.setColor(color);
		g.fill(circle);
	}

	public void drawVelocity(Graphics2D g) {
		int x = (int) (this.getX());
		int y = (int) (this.getY());
		velocity.scaleVector(3).drawSelf(g, x, y, Color.RED);
	}

	public void drawAcceleration(Graphics2D g) {
		int x = (int) (position.addVector(velocity.scaleVector(3)).getX());
		int y = (int) (position.addVector(velocity.scaleVector(3)).getY());
		acceleration.scaleVector(200).drawSelf(g, x, y, Color.YELLOW);
	}

	public void checkInBounds(int width, int height) {
		if (getX() > width || getX() < 0) {
			setVelocity(new Vector(-velocity.getX(), velocity.getY()));
			if (getX() < 0)
				position = new Vector(0,	 getY());
			else
				position = new Vector(width, getY());
		}
		if (getY() < 0 || getY() > height) {
			setVelocity(new Vector(velocity.getX(), -velocity.getY()));
			if (getY() < 0)
				position = new Vector(getX(), 0);
			else
				position = new Vector(getX(), height);
		}
	}

	public void tick() {
		velocity = velocity.addVector(acceleration);
		position = position.addVector(velocity);
		circle = new Ellipse2D.Double(getX(), getY(), width, width);
	}

}
