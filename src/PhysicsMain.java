import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class PhysicsMain {
	public static final int EDGE_BORDER = 3;

	public static JFrame frame = new JFrame("Physics Engine");
	public static final int MAX_SPAWN = 500;
	public static final int X = 500;
	public static final int Y = 500;
	public static final double GRAVITY = 1500;
	public static final double DRAG = 0.2;
	public static final double BOUNCE = 0.9;
	public static BufferedImage image = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
	public static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static Area down = new Area();
	public static Area left = new Area();
	public static Area up = new Area();
	public static Area right = new Area();

	public static BufferStrategy buffer;
	private static Canvas canvas;
	private static GraphicsEnvironment ge;
	private static GraphicsDevice gd;
	private static GraphicsConfiguration gc;
	private static Graphics graphics;
	private static Graphics2D g2d;
	private static AffineTransform at;

	public static boolean isRunning = true;

	private static java.awt.Point lastFrameLocation;

	public static void main(String[] args) {
		initalizeFrame();
		
		Thread moveEngine = new MoveEngine();
		moveEngine.start();
		Thread makeBall = new MakeBall();
		makeBall.start();
		Thread makeBox = new MakeBox();
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

				g2d.setColor(Color.RED);
				g2d.fill(down);
				g2d.setColor(Color.RED);
				g2d.fill(left);
				g2d.setColor(Color.RED);
				g2d.fill(right);
				g2d.setColor(Color.RED);
				g2d.fill(up);
				// Draw entities
				for (int i = 0; i < entities.size(); i++) {
					g2d.setColor(entities.get(i).color());
					at = new AffineTransform();
					if (entities.get(i) instanceof Ball) {
						Ball s = (Ball) entities.get(i);
						at.translate(s.getX(), s.getY());
						g2d.fill(new Ellipse2D.Double(s.getX(), s.getY(), s.getRadius() * 2, s.getRadius() * 2));
					} else if (entities.get(i) instanceof Box) {
						Box s = (Box) entities.get(i);

						s.updateRect();

						g2d.fill(s.getRotated());
					}
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

	public static synchronized int createBox(int x, int y, double vx, double vy, double bearing, double spin, int width,
			int height, int m, Color color) {
		if (entities.size() >= MAX_SPAWN)
			return 1;
		entities.add(new Box(x, y, vx, vy, bearing, spin, width, height, m, color));
		return 0;
	}

	public static void initalizeFrame() {
		Polygon temp = new Polygon();
		temp.addPoint(X, Y);
		temp.addPoint(X, Y - EDGE_BORDER);
		temp.addPoint(0, Y - EDGE_BORDER);
		temp.addPoint(0, Y);
		down = new Area(temp);
		temp.reset();
		temp.addPoint(0, Y);
		temp.addPoint(EDGE_BORDER, Y);
		temp.addPoint(EDGE_BORDER, 0);
		temp.addPoint(0, 0);
		left = new Area(temp);
		temp.reset();
		temp.addPoint(0, 0);
		temp.addPoint(0, EDGE_BORDER);
		temp.addPoint(X, EDGE_BORDER);
		temp.addPoint(X, 0);
		up = new Area(temp);
		temp.reset();
		temp.addPoint(X, 0);
		temp.addPoint(X - EDGE_BORDER, 0);
		temp.addPoint(X - EDGE_BORDER, Y);
		temp.addPoint(X, Y);
		right = new Area(temp);

		frame.setVisible(true);
		frame.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				lastFrameLocation = frame.getLocation();
				System.out.println(lastFrameLocation);
			}
		});

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
