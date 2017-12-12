import java.awt.Color;

public class MakeBox extends Thread {
	public void run() {
		while (PhysicsMain.isRunning) {
			PhysicsMain.createBox(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
					Math.random() * 1000.0 - 500, Math.toRadians(Math.random() * 90 - 45), 50, 50, 100, Color.BLACK);
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
