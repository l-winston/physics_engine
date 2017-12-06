import java.awt.Color;

public abstract class Entity {
	public Vector velocity;
	public double x;
	public double y;
	public double mass;
	public Color color;

	/**
	 * bearing of Entity (in radians)
	 */
	public double direction;
	
	/**
	 * repaints Entity
	 */
	public abstract void update();
}
