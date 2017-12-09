import java.awt.geom.Point2D;

public class Box extends Entity {
	public double height;
	public double width;
	public Point2D c1;
	public Point2D c2;
	public Point2D c3;
	public Point2D c4;

	/*
	 * with c1----------c2 | | | | height | | c4----------c3
	 */

	public Box(int x, int y, double vx, double vy, double bearing, int m) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.bearing = bearing;
		this.height = 15;
		this.width = 15;
		findCorners();
	}

	public void findCorners() {
		double hypotenuse = Math.sqrt(Math.pow(this.width / 2, 2) + Math.pow(this.height / 2, 2));
		this.c1 = new Point2D.Double(this.x + Math.sin(this.bearing - Math.atan(this.width / this.height)) * hypotenuse,
				this.y + Math.cos(this.bearing - Math.atan(this.width / this.height)) * hypotenuse);
		this.c2 = new Point2D.Double(this.x + Math.cos(this.bearing - Math.atan(this.height / this.width)) * hypotenuse,
				this.y + Math.sin(this.bearing - Math.atan(this.height / this.width)) * hypotenuse);
		this.c3 = new Point2D.Double(this.x - Math.sin(this.bearing - Math.atan(this.width / this.height)) * hypotenuse,
				this.y - Math.cos(this.bearing - Math.atan(this.width / this.height)) * hypotenuse);
		this.c4 = new Point2D.Double(this.x - Math.cos(this.bearing - Math.atan(this.height / this.width)) * hypotenuse,
				this.y - Math.sin(this.bearing - Math.atan(this.height / this.width)) * hypotenuse);
	}

	public Box(int x, int y) {
		this(x, y, 0.0, 0.0, 0, 100);
	}

	public Vector2D velVector() {
		return new Vector2D(this.vx(), this.vy());
	}

	public int dimX() {
		return (int) (this.width * 2);
	}

	public int dimY() {
		return (int) (this.height * 2);
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
}