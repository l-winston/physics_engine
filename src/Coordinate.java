

public class Coordinate {
	public static double x;
	public static double y;
	public Coordinate(double x, double y){
		this.x = x;
		this.y = y;
	}
//	public Coordinate(int x, int y){
//		this.x = (double) x;
//		this.y = (double) y;
//	}
	public static boolean equals(Coordinate c){
		return x == c.x && y == c.y;
	}
	public String toString(){
		return x + " " + y;
	}
	public static Coordinate toImageCoordinates(){
		return new Coordinate(x + PhysicsMain.image.getWidth() / 2.0, PhysicsMain.image.getHeight() / 2.0 - 1 - y);
	}
	public static Coordinate toCartesianCoordinates(){
		return new Coordinate(x - PhysicsMain.image.getWidth() / 2.0, PhysicsMain.image.getHeight() / 2.0 - 1 - y);
	}
}
