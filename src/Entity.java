import java.awt.Color;

public abstract class Entity {
	public Point velocity;
	public Point location;
	public double mass;
	public Color color;
	public double angular_velocity;
	public boolean exists;

	/**
	 * bearing of Entity (in radians)
	 */
	public double direction;

	/**
	 * repaints Entity
	 */
	public abstract void update();
}
