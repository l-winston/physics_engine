import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Entity {
	public ArrayList<Accel> accelerations = new ArrayList<Accel>();
	public ArrayList<Spring> springs = new ArrayList<Spring>();

	public double x, y, vx, vy;
	public double mass;
	public Color color;
	public double bearing;
	public double spin;

	public double direction;

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public Point2D getCenter() {
		return new Point2D.Double(this.x, this.y);
	}

	public double vx() {
		return this.vx;
	}

	public double vy() {
		return this.vy;
	}

	public double bearing() {
		return this.bearing;
	}

	public double spin() {
		return this.spin;
	}

	public Color color() {
		return this.color;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public void updateVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	/**
	 * @return velocity vector (x, y)
	 */
	public Vector2D velVector() {
		return new Vector2D(this.vx(), this.vy());
	}

	public void updatePos(double newX, double newY) {
		this.x = newX;
		this.y = newY;
	}

	public void updateBearing(double bearing) {
		this.bearing = bearing;
	}

	public void updateSpin(double spin) {
		this.spin = spin;
	}

	public void addAccel(Accel a) {
		this.accelerations.add(a);
	}

	public Accel sumAccel() {
		double xAccel = 0, yAccel = 0;
		for (int i = 0; i < this.accelerations.size(); i++) {
			xAccel += this.accelerations.get(i).ax();
			yAccel += this.accelerations.get(i).ay();
		}
		this.accelerations.clear();
		return new Accel(xAccel, yAccel);
	}

	public void applyDrag(double drag) {
		this.vx = (drag * this.vx);
		this.vy = (drag * this.vy);
		this.spin = (drag * this.spin);
	}

	public void createSpring(Entity B, double k, Color c) {
		this.springs.add(new Spring(this, B, k, c));
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
		System.out.println(x + " " + y);

		return new Point(x, y);
	}
}
