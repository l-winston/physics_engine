import java.awt.geom.Point2D;

public class Ball extends Entity {
	private double radius;

	public Ball(int x, int y, double vx, double vy, double radius, int m) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		//this.radius = Math.random()*5+15;
		this.radius = 10;
	}

	public Ball(int x, int y) {
		this(x, y, 0.0, 0.0, 15, 100);
	}

	public Vector2D velVector() {
		return new Vector2D(this.vx(), this.vy());
	}


	public int dimX() {
		return (int) (this.radius * 2);
	}

	public int dimY() {
		return (int) (this.radius * 2);
	}

	public Point2D getCenter() {
		return new Point2D.Double(this.x + (this.dimX() / 2), this.y + (this.dimY() / 2));
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