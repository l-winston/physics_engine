
public class Point {
	public double x;
	public double y;
	public boolean isPolar;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		//this.isPolar = isPolar;
	}

	public Point add(Point v) {
		return new Point(x + v.x, y + v.y);
	}

	public void getUnitVectors() {
		double x1 = 0;
		double y1 = 0;
		if (y <= Math.PI / 2) {
			x1 = Math.sin(y) * x;
			y1 = Math.cos(y) * x;
		} else if (y <= Math.PI) {
			y1 = -Math.sin(y - Math.PI / 2) * x;
			x1 = Math.cos(y - Math.PI / 2) * x;
		} else if (y <= Math.PI * 3 / 2) {
			x1 = -Math.cos(Math.PI * 3 / 2 - y) * x;
			y1 = -Math.sin(Math.PI * 3 / 2 - y) * x;
		} else if (y <= Math.PI * 2) {
			x1 = -Math.cos(y - Math.PI * 3 / 2) * x;
			y1 = Math.sin(y - Math.PI * 3 / 2) * x;
		}
		x = x1;
		y = y1;
	}

	public void getPolar() {
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double bearing = 0;

		if (x >= 0 && y >= 0) {
			bearing = Math.atan(x / y);
		} else if (x >= 0 && y <= 0) {
			bearing = Math.PI / 2 + Math.atan(Math.abs(y / x));
		} else if (x <= 0 && y <= 0) {
			bearing = Math.PI + Math.atan(Math.abs(x / y));
		}else if(x <= 0 && y >= 0) {
			bearing = Math.PI*3/2 + Math.atan(Math.abs(y/x));
		}

		this.x = magnitude;
		this.y = bearing;
	}

	public String toString() {
		return x + " " + y;
	}
}
