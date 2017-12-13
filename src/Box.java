import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Box extends Entity {
	public double height;
	public double width;
	public Polygon rect;
	public Shape rotated;
	public static int temp = 0;

	public Box(int x, int y, double vx, double vy, double bearing, double spin, int height, int width, int m,
			Color color) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.bearing = bearing;
		this.spin = spin;
		this.width = width;
		this.height = height;
		this.color = color;
		rect = new Polygon();
		rect.addPoint((int) Math.round(this.getCenter().getX() - this.width / 2),
				(int) Math.round(this.getCenter().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getCenter().getX() + this.width / 2),
				(int) Math.round(this.getCenter().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getCenter().getX() + this.width / 2),
				(int) Math.round(this.getCenter().getY() - this.height / 2));
		rect.addPoint((int) Math.round(this.getCenter().getX() - this.width / 2),
				(int) Math.round(this.getCenter().getY() - this.height / 2));

		AffineTransform at = new AffineTransform();

		at.rotate(this.bearing(), this.getCenter().getX(), this.getCenter().getY());
		rotated = at.createTransformedShape(this.getRect());
	}

	public Box(int x, int y) {
		this(x, y, 0.0, 0.0, 0, 0, 10, 10, 100, Color.BLACK);
	}

	public Vector2D velVector() {
		return new Vector2D(this.vx(), this.vy());
	}

	/**
	 * @return center of rectangle
	 */
	public Point2D getCenter() {
		return new Point2D.Double(this.x + (this.getWidth() / 2), this.y + (this.getHeight() / 2));
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public double getX2() {
		return (this.x + this.getWidth());
	}

	public double getY2() {
		return (this.y + this.getHeight());
	}

	public void updateRect() {
		//update the rectangle (takes into account x/y translation)
		rect = new Polygon();
		rect.addPoint((int) Math.round(this.getCenter().getX() - this.width / 2),
				(int) Math.round(this.getCenter().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getCenter().getX() + this.width / 2),
				(int) Math.round(this.getCenter().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getCenter().getX() + this.width / 2),
				(int) Math.round(this.getCenter().getY() - this.height / 2));
		rect.addPoint((int) Math.round(this.getCenter().getX() - this.width / 2),
				(int) Math.round(this.getCenter().getY() - this.height / 2));
		
		//transform the rectangle (takes into account bearing)
		AffineTransform at = new AffineTransform();

		at.rotate(this.bearing(), this.getCenter().getX(), this.getCenter().getY());
		rotated = at.createTransformedShape(this.getRect());

	}

	public Shape getRotated() {
		return this.rotated;
	}

	public Polygon getRect() {
		return this.rect;
	}
}