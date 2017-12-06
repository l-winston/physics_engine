import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class PhysicsMain {
	public static JFrame frame = new JFrame("Physics Engine");
	public static BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
	static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static void main(String[] args) {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();

		setWhite();
		Box red = new Box(50, 50, new Vector(1, 0), 0, 0, 100.0, new Color(255, 0, 0), Math.toRadians(0));
		entities.add(red);
		Box green = new Box(50, 50, new Vector(0, 0), -50, -50, 100.0, new Color(0, 255, 0), Math.toRadians(0));
		entities.add(green);
		
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
			e.direction += Math.toRadians(1);
		}
		frame.pack();
		frame.repaint();
		//entities.get(0).direction += Math.toRadians(1);

	}
}
