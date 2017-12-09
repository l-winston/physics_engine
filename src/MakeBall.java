public class MakeBall extends Thread
{
  public void run()
  {
    while (PhysicsMain.isRunning) {
      PhysicsMain.create(PhysicsMain.X/2, PhysicsMain.Y/2-150, Math.random() * 1000.0-500,
                     Math.random() * 1000.0-500, 100);
      try 
      {
        sleep(250);
      } 
      catch (InterruptedException e) 
      {
      }
    }
  }
}