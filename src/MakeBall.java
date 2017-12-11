public class MakeBall extends Thread {
	public void run() {
		while (PhysicsMain.isRunning) {
			PhysicsMain.createBall(PhysicsMain.X / 2, PhysicsMain.Y / 2 - 300, Math.random() * 1000.0 - 500,
					Math.random() * 1000.0 - 500, Math.random()*10+5, 100);
			try {
				sleep(250);
			} catch (InterruptedException e) {
			}
		}
	}
}