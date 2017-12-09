import java.awt.geom.Point2D;

public class Box extends Entity {
	public double height;
	public double width;

	/*
	 * with c1----------c2 | | | | height | | c4----------c3
	 */

	public Box(int x, int y, double vx, double vy, double bearing, int height, int width, int m) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.bearing = bearing;
		this.width = width;
		this.height = height;
	}

	public Box(int x, int y) {
		this(x, y, 0.0, 0.0, 0, 10, 10, 100);
	}

	public Vector2D velVector() {
		return new Vector2D(this.vx(), this.vy());
	}

	public int dimX() {
		return (int) (this.width);
	}

	public int dimY() {
		return (int) (this.height);
	}

	public Point2D getCenter() {
		return new Point2D.Double(this.x + (this.dimX() / 2), this.y + (this.dimY() / 2));
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public double getX2() {
		return (this.x + this.dimX());
	}

	public double getY2() {
		return (this.y + this.dimY());
	}
	
	public double getBearing(){
		return this.bearing;
	}
}