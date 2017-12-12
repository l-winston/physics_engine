import java.awt.Color;
import java.util.ArrayList;

public abstract class Entity {
	public ArrayList<Accel> accelerations = new ArrayList<Accel>();

	public double x, y, vx, vy;
	public double mass;
	public Color color;
	public double bearing;
	public double spin;
	public double angular_velocity;

	/**
	 * bearing of Entity (in radians)
	 */
	public double direction;

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
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

	public void updatePos(double newX, double newY) {
		this.x = newX;
		this.y = newY;
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
	}
}
