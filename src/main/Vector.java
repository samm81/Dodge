package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Vector {

	private double x;
	private double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public double getAngle() {
		return Math.atan(y / x);
	}
	
	public Vector scaleVector(double scaleFactor){
		return new Vector(x * scaleFactor, y * scaleFactor);
	}

	public Vector addVector(Vector vector) {
		double x = this.getX() + vector.getX();
		double y = this.getY() + vector.getY();
		return new Vector(x, y);
	}

	public Vector subtractVector(Vector vector) {
		double x = this.getX() - vector.getX();
		double y = this.getY() - vector.getY();
		return new Vector(x, y);
	}
	
	public void drawSelf(Graphics2D g, int x, int y, Color color){
		int x2 = x + (int) (this.getX());
		int y2 = y + (int) (this.getY());
		g.setColor(color);
		g.setStroke(new BasicStroke(2));
		g.drawLine(x, y, x2, y2);
	}

}
