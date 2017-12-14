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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ball B = new Ball(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
				Math.random() * 1000.0 - 500, 15, 100, Color.RED);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ball C = new Ball(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
				Math.random() * 1000.0 - 500, 15, 100, Color.GREEN);
		
		A.createSpring(B, 1, Color.BLUE);
		B.createSpring(A, 1, Color.BLUE);
		A.createSpring(C, 1, Color.BLUE);
		C.createSpring(A, 1, Color.BLUE);
		C.createSpring(B, 1, Color.BLUE);
		B.createSpring(C, 1, Color.BLUE);
		PhysicsMain.entities.add(A);
		PhysicsMain.entities.add(B);
		PhysicsMain.entities.add(C);

	}
}