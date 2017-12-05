import java.awt.Color;

public abstract class Entity {
	public static Vector velocity;
	public static double x;
	public static double y;
	public static double mass;
	public static Color color;
	
	public abstract void update();
}
