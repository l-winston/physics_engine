import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Box extends Entity {
	public double height;
	public double width;

	public Box(double height, double width, Vector velocity, double x, double y, double mass, Color color) {
		this.height = height;
		this.width = width;
		this.velocity = new Vector(velocity.x, velocity.y);
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.color = color;
	}

	public void update() {
		JFrame frame = PhysicsMain.frame;
		BufferedImage image = PhysicsMain.image;
		int image_width = image.getWidth();
		int image_height = image.getHeight();

		for (int x = -image_width / 2; x < image_width / 2; x++) {
			for (int y = -image_height / 2; y < image_height / 2; y++) {
				if (x < this.x + width / 2 && x > this.x - width / 2 && y < this.y + height / 2
						&& y > this.y - height / 2) {

					double cx = x + image_width / 2.0;
					double cy = image_height / 2.0 - 1 - y;

					image.setRGB((int) Math.round(cx), (int) Math.round(cy), color.getRGB());
				}
			}
		}

		this.x += velocity.x;
		this.y += velocity.y;
		velocity.y -= 0.1;// gravity

	}

}
