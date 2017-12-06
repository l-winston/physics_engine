
public class tester {
	public static void main(String[] args) {
		Point v = new Point(-1, 5);
		System.out.println("initial: " + v);
		v.getPolar();
		System.out.println("after polarized: " + v);
		v.getUnitVectors();
		System.out.println("end: " + v);

	}
}
