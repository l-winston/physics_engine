import java.awt.Color;

public class Spring {
	public Entity A;
	public Entity B;
	public Color color;
	
	/**
	 * k is the spring constant
	 */
	public double k;
	
	public Spring(Entity A, Entity B, double k, Color c){
		this.A = A;
		this.B = B;
		this.k = k;
		this.color = c;
	}
}
