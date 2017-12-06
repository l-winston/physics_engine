
public class Vector {
	public double x;
	public double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}

	public void getUnitVectors() {
		double y1 = x * Math.cos(Math.PI/4 - y);
		double x1 = x * Math.sin(y);
		x = x1;
		y = y1;
	}

	public void getPolar() {
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double bearing = Math.atan(x / y);
		this.x = magnitude;
		this.y = bearing;
	}

	public String toString() {
		return x + " " + y;
	}
}
