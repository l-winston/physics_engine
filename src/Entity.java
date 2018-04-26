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

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getVX() {
		return this.vx;
	}

	public double getVY() {
		return this.vy;
	}

	public Vector2D getVelocityVector() {
		return new Vector2D(this.getVX(), this.getVY());
	}

	public double getBearing() {
		return this.bearing;
	}
	
	public double getRotationalVelocity() {
		return this.rotationalVelocity;
	}

	public Point2D getCenter() {
		return new Point2D.Double(this.x, this.y);
	}

	public Color color() {
		return this.color;
	}

	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public void setPosition(double newX, double newY) {
		this.x = newX;
		this.y = newY;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public void setRotationalVelocity(double rotationalVelocity) {
		this.rotationalVelocity = rotationalVelocity;
	}

	public void addForce(Force a) {
		this.Forces.add(a);
	}

	public Force sumForce() {
		double xForce = 0, yForce = 0;
		for (int i = 0; i < this.Forces.size(); i++) {
			xForce += this.Forces.get(i).ax();
			yForce += this.Forces.get(i).ay();
		}
		this.Forces.clear();
		return new Force(xForce, yForce);
	}

	public void createSpring(Entity B, double k, Color c) {
		this.springs.add(new Spring(this, B, k, c));
		B.springs.add(new Spring(B, this, k, c));
	}

	public Point getSpringForces() {
		double x = 0;
		double y = 0;

		for (Spring s : this.springs) {
			Point2D aCenter = s.A.getCenter();
			Point2D bCenter = s.B.getCenter();

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

		return new Point(x, y);
	}
}
