import java.awt.Color;
import java.awt.geom.Point2D;

public class Ball extends Entity {
	private double radius;

	public Ball(int x, int y, double vx, double vy, double radius, int m, Color c) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.color = c;
	}

	public Ball(int x, int y) {
		this(x, y, 0.0, 0.0, 15, 100, Color.BLACK);
	}

	public int dimX() {
		return (int) (this.radius * 2);
	}

	public int dimY() {
		return (int) (this.radius * 2);
	}

	public double getRadius() {
		return this.radius;
	}

	public double getX2() {
		return (this.x + this.dimX());
	}

	public double getY2() {
		return (this.y + this.dimY());
	}
}
