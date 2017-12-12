import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class PhysicsMain {
	public static JFrame frame = new JFrame("Physics Engine");
	public static final int MAX_SPAWN = 300;
	public static final int X = 500;
	public static final int Y = 500;
	public static final double GRAVITY = 1500;
	// public static final double GRAVITY = 1500;
	public static final double DRAG = 0.2;
	public static final double BOUNCE = 0.9;
	public static BufferedImage image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
	public static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static BufferStrategy buffer;
	private static Canvas canvas;
	private static GraphicsEnvironment ge;
	private static GraphicsDevice gd;
	private static GraphicsConfiguration gc;
	private static Graphics graphics;
	private static Graphics2D g2d;
	private static AffineTransform at;

	public static boolean isRunning = true;

	public static void main(String[] args) {
		initalizeFrame();

		Thread moveEngine = new MoveEngine();
		moveEngine.start();
		Thread makeBall = new MakeBall();
		makeBall.start();
		//Thread makeBox = new MakeBox();
		//makeBox.start();

		runAnimation();
	}

	public static void runAnimation() {
		// Set up some variables
		int fps = 0;
		int frames = 0;
		long totalTime = 0;
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;
		// Start the loop.
		while (isRunning) {
			try {
				// Calculations for FPS.
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				totalTime += curTime - lastTime;
				if (totalTime > 1000) {
					totalTime -= 1000;
					fps = frames;
					frames = 0;
				}
				++frames;
				// clear back buffer...
				g2d = image.createGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, X, Y);
				// Draw entities
				for (int i = 0; i < entities.size(); i++) {
					at = new AffineTransform();
					at.translate(entities.get(i).getX(), entities.get(i).getY());
					Ball s = (Ball) entities.get(i);
					g2d.setColor(s.color);
					//Box s = (Box) entities.get(i);
					g2d.fill(new Ellipse2D.Double(s.getX(), s.getY(), s.getRadius() * 2, s.getRadius() * 2));
					//Polygon rect = new Polygon();
					//rect.addPoint((int) Math.round(s.getCenter().getX()-s.width/2), (int) Math.round(s.getCenter().getY()+s.height/2));
					//rect.addPoint((int) Math.round(s.getCenter().getX()+s.width/2), (int) Math.round(s.getCenter().getY()+s.height/2));
					//rect.addPoint((int) Math.round(s.getCenter().getX()+s.width/2), (int) Math.round(s.getCenter().getY()-s.height/2));
					//rect.addPoint((int) Math.round(s.getCenter().getX()-s.width/2), (int) Math.round(s.getCenter().getY()-s.height/2));
					//Rectangle bounds = rect.getBounds();
					//at.rotate(s.getBearing(), s.getCenter().getX(), s.getCenter().getY());
					//Shape rotated = at.createTransformedShape(rect);
					
					at.translate(s.getX(), s.getY());
					//g2d.fill(rotated);
					//g2d.dispose();
				}
				// display frames per second...
				g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
				g2d.setColor(Color.GREEN);
				g2d.drawString(String.format("FPS: %s", fps), 20, 20);
				// Blit image and flip...
				graphics = buffer.getDrawGraphics();
				graphics.drawImage(image, 0, 0, null);
				if (!buffer.contentsLost())
					buffer.show();
				// Let the OS have a little time...
				Thread.sleep(15);
			} catch (InterruptedException e) {
			} finally {
				// release resources
				if (graphics != null)
					graphics.dispose();
				if (g2d != null)
					g2d.dispose();
			}
		}
	}

	public static boolean allDead() {
		if (entities.size() < 1)
			return true;
		return false;
	}

	public static synchronized int createBall(int x, int y, double vx, double vy, double radius, int m, Color c) {
		if (entities.size() >= MAX_SPAWN)
			return 1;
		entities.add(new Ball(x, y, vx, vy, radius, m, c));
		return 0;
	}

	public static synchronized int createBox(int x, int y, double vx, double vy, double bearing, int width, int height, int m) {
		if (entities.size() >= MAX_SPAWN)
			return 1;
		entities.add(new Box(x, y, vx, vy, bearing, width, height, m));
		return 0;
	}

	public static void initalizeFrame() {
		frame.setVisible(true);
		frame.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(X, Y);

		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		// setup BufferStrategy for double buffering
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();
		// Get graphics configuration...
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		gc = gd.getDefaultConfiguration();
		// Create off-screen drawing surface
		image = gc.createCompatibleImage(X, Y);
		// Objects needed for rendering...
		graphics = null;
		g2d = null;
	}
}
