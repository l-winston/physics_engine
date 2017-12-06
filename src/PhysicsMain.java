import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class PhysicsMain {
	public static JFrame frame = new JFrame("Physics Engine");
	public static BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
	public static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static void main(String[] args) {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();

		setWhite();
		Box red = new Box(50, 50, new Point(0, 0), 50, 250, 100.0, new Color(255, 0, 0), Math.toRadians(45), Math.toRadians(5), true);
		entities.add(red);
		Box green = new Box(50, 50, new Point(0, 0), -50, 250, 100.0, new Color(0, 255, 0), Math.toRadians(0), Math.toRadians(2), true);
		entities.add(green);

		//Box blue = new Box(50, 50, new Vector(0, 5), 0, -100, 100.0, new Color(0, 0, 255), Math.toRadians(0), Math.toRadians(0.5), true);
		//entities.add(blue);

		while (true) {
			update();
		}
	}

	public static void setWhite() {
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				image.setRGB(i, j, new Color(255, 255, 255).getRGB());
			}
		}
		image.setRGB(image.getWidth()/2, image.getHeight()/2, new Color(0, 0, 255).getRGB());
	}

	public static void update() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setWhite();
		for (Entity e : entities) {
			e.update();
		}
		frame.pack();
		frame.repaint();

	}
}
