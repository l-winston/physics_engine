import java.awt.Color;

public class MakeBall extends Thread {
	public void run() {
		while (PhysicsMain.isRunning) {
			PhysicsMain.createBall(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
					Math.random() * 1000.0 - 500, 15, 100, Color.BLACK);
			try {
				sleep(150);
			} catch (InterruptedException e) {
			}
		
		}
	}
}