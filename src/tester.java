
public class tester {
	public static void main(String[] args) {
		Vector v = new Vector(-1, 5);
		System.out.println("initial: " + v);
		v.getPolar();
		System.out.println("after polarized: " + v);
		v.getUnitVectors();
		System.out.println("end: " + v);

	}
}
