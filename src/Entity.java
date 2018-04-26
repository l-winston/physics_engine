import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Entity {
	public ArrayList<Force> Forces = new ArrayList<Force>();
	public ArrayList<Spring> springs = new ArrayList<Spring>();
	public ArrayList<Torque> Torque = new ArrayList<Torque>();

	public double x, y, vx, vy;
	public double mass;
	public Color color;
	public double bearing, rotationalVelocity; // ω is rotational velocity, (+)
												// is ccw and (-) is cw

	/**
	 * @return x position of entity
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @return y position of entity
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @return x component of entity's velocity
	 */
	public double getVX() {
		return this.vx;
	}

	/**
	 * @return y component of entity's velocity
	 */
	public double getVY() {
		return this.vy;
	}

	/**
	 * @return velocity of entity in Vector2D form
	 */
	public Vector2D getVelocityVector() {
		return new Vector2D(this.getVX(), this.getVY());
	}

	/**
	 * @return angle (orientation) of entity
	 */
	public double getBearing() {
		return this.bearing;
	}
	
	/**
	 * @return rotational velocity of entity
	 */
	public double getRotationalVelocity() {
		return this.rotationalVelocity;
	}

	/**
	 * @return position (x, y) of entity in Point2D form
	 */
	public Point2D getPosition() {
		return new Point2D.Double(this.x, this.y);
	}

	/**
	 * @return color of entity
	 */
	public Color color() {
		return this.color;
	}

	/**
	 * @param vx new x component of velocity
	 * @param vy new y component of velocity
	 */
	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	/**
	 * @param x new x position
	 * @param y new y position
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param bearing new bearing of entity
	 */
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	/**
	 * @param rotationalVelocity new rotational velocity of entity
	 */
	public void setRotationalVelocity(double rotationalVelocity) {
		this.rotationalVelocity = rotationalVelocity;
	}

	/**
	 * @param f the force to be added
	 */
	public void addForce(Force f) {
		this.Forces.add(f);
	}
 
	
	/**
	 * @return returns sum of all forces acting on this entity
	 */
	public Force sumForce() {
		double xForce = 0, yForce = 0;
		for (int i = 0; i < this.Forces.size(); i++) {
			xForce += this.Forces.get(i).fx();
			yForce += this.Forces.get(i).fy();
		}
		this.Forces.clear();
		return new Force(xForce, yForce);
	}

	/**
	 * @param B spring to attach this entity to
	 * @param k spring constant
	 * @param c color of spring
	 */
	public void createSpring(Entity B, double k, Color c) {
		this.springs.add(new Spring(this, B, k, c));
		B.springs.add(new Spring(B, this, k, c));
	}

	public Force getSpringForces() {
		double x = 0;
		double y = 0;

		for (Spring s : this.springs) {
			Point2D aCenter = s.A.getPosition();
			Point2D bCenter = s.B.getPosition();

			double relX = bCenter.getX() - aCenter.getX();
			double relY = bCenter.getY() - aCenter.getY();

			Point p = new Point(relX, relY);
			p.getPolar();
			// set magnitude
			p.x *= s.k;
			p.getUnitVectors();
			x += p.x;
			y += p.y;

		}

		return new Force(x, y);
	}
}
