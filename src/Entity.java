import java.awt.Color;

public abstract class Entity {
	public Vector velocity;
	public double x;
	public double y;
	public double mass;
	public Color color;
	
	public abstract void update();
}
