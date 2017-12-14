import java.awt.Color;

public class MakeBall extends Thread {
	public void run() {
//		while (PhysicsMain.isRunning) {
//			PhysicsMain.createBall(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
//					Math.random() * 1000.0 - 500, 15, 100, Color.BLACK);
//			try {
//				sleep(500);
//			} catch (InterruptedException e) {
//			}
//
//		}

		Ball A = new Ball(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
				Math.random() * 1000.0 - 500, 15, 100, Color.BLACK);
		Ball B = new Ball(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
				Math.random() * 1000.0 - 500, 15, 100, Color.RED);
		A.createSpring(B, 0.0001);
		B.createSpring(A, 0.0001);
		PhysicsMain.entities.add(A);
		PhysicsMain.entities.add(B);
	}
}