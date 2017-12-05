
public class Vector {
	public double x;
	public double y;
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector add (Vector v){
		return new Vector(x + v.x, y + v.y);
	}
	
	public Vector getUnitVectors(double magnitude, double bearing){
		return new Vector(magnitude*Math.cos(bearing), magnitude*Math.sin(bearing));
	}
	
	public String toString(){
		return x + " " + y;
	}
}
