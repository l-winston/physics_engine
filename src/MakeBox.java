
public class MakeBox extends Thread{
	public void run() {
		while (PhysicsMain.isRunning) {
			PhysicsMain.createBox(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 150, 0, Math.random() * 1000.0 - 500,
					Math.random() * 1000.0 - 500, 100);
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
