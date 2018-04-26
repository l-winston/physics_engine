import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Box extends Entity {
	public static final double BOUNCE = PhysicsMain.BOUNCE;
	
	/**
	 * side length (height)
	 */
	public double height;
	
	/**
	 * side length (width)
	 */
	public double width;
	
	/**
	 * this box, created around 0, 0 and translated to its center's coordinates
	 */
	public Polygon rect;
	
	/**
	 * this box, rotated
	 */
	public Shape rotated;
	
	public Box(int x, int y, double vx, double vy, double bearing, double rotationalVelocity, int height, int width, int m,
			Color color) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.bearing = bearing;
		this.rotationalVelocity = rotationalVelocity;
		this.width = width;
		this.height = height;
		this.color = color;
		
		rect = new Polygon();
		rect.addPoint((int) Math.round(this.getPosition().getX() - this.width / 2),
				(int) Math.round(this.getPosition().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getPosition().getX() + this.width / 2),
				(int) Math.round(this.getPosition().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getPosition().getX() + this.width / 2),
				(int) Math.round(this.getPosition().getY() - this.height / 2));
		rect.addPoint((int) Math.round(this.getPosition().getX() - this.width / 2),
				(int) Math.round(this.getPosition().getY() - this.height / 2));

		AffineTransform at = new AffineTransform();

		at.rotate(this.getBearing(), this.getPosition().getX(), this.getPosition().getY());
		rotated = at.createTransformedShape(this.getRect());
	}

	public Box(int x, int y) {
		this(x, y, 0.0, 0.0, 0, 0, 10, 10, 100, Color.BLACK);
	}

	/**
	 * @return side length (width)
	 */
	public double getWidth() {
		return this.width;
	}

	/**
	 * @return side length (width)
	 */
	public double getHeight() {
		return this.height;
	}

	/**
	 * @return greatest x coordinate of this Box
	 */
	public double maxX() {
		return this.rotated.getBounds2D().getMaxX();
	}

	/**
	 * @return least x coordinate of this Box
	 */
	public double minX() {
		return this.rotated.getBounds2D().getMinX();
	}
	
	/**
	 * @return greatest y coordinate of this Box
	 */
	public double maxY() {
		return this.rotated.getBounds2D().getMaxY();
	}
	
	/**
	 * @return least y coordinate of this Box
	 */
	public double minY() {
		return this.rotated.getBounds2D().getMinY();
	}

	public void updateRect() {
		//update the rectangle (takes into account x/y translation)
		rect = new Polygon();
		rect.addPoint((int) Math.round(this.getPosition().getX() - this.width / 2),
				(int) Math.round(this.getPosition().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getPosition().getX() + this.width / 2),
				(int) Math.round(this.getPosition().getY() + this.height / 2));
		rect.addPoint((int) Math.round(this.getPosition().getX() + this.width / 2),
				(int) Math.round(this.getPosition().getY() - this.height / 2));
		rect.addPoint((int) Math.round(this.getPosition().getX() - this.width / 2),
				(int) Math.round(this.getPosition().getY() - this.height / 2));
		
		//transform the rectangle (takes into account bearing)
		AffineTransform at = new AffineTransform();

		at.rotate(this.getRotationalVelocity(), this.getPosition().getX(), this.getPosition().getY());
		rotated = at.createTransformedShape(this.getRect());

	}

	/**
	 * @return this rectangle, rotated bearing degrees (radians)
	 */
	public Shape getRotated() {
		return this.rotated;
	}

	/**
	 * @return this rectangle, translated to its center
	 */
	public Polygon getRect() {
		return this.rect;
	}
}