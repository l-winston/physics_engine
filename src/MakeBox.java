
public class MakeBox extends Thread{
	public void run() {
		while (PhysicsMain.isRunning) {
			PhysicsMain.createBox(100, 100, Math.random() * 1000.0 - 500,
					Math.random() * 1000.0 - 500, Math.toRadians(Math.random()*90 - 45), 100);
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
