
public class MakeBox extends Thread{
	public void run() {
		while (PhysicsMain.isRunning) {
			PhysicsMain.createBox(100, 100, Math.random() * 100.0 - 50,
					Math.random() * 100.0 - 50, Math.toRadians(Math.random()*90 - 45), 50, 50, 100);
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
