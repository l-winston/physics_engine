import java.awt.Color;
import java.awt.image.BufferedImage;

public class Box extends Entity {
	public double height;
	public double width;
	public static BufferedImage image = PhysicsMain.image;

	public Box(double height, double width, Point velocity, double x, double y, double mass, Color color,
			double direction, double angular_velocity, boolean exists) {
		this.height = height;
		this.width = width;
		this.velocity = new Point(velocity.x, velocity.y);
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.color = color;
		this.direction = direction;
		this.angular_velocity = angular_velocity;
		this.exists = exists;
	}

	public void update() {
		updateLocation();
		updateDisplay();
	}

	private void updateLocation() {
		for (Entity e : PhysicsMain.entities) {
			// velocity.y -= 1 / Math.pow((y - e.y), 2) * (y - e.y) /
			// Math.abs((y - e.y));// gravitation
			// velocity.x -= 1 / Math.pow((x - e.x), 2) * (x - e.x) /
			// Math.abs((x - e.x));

			// this.velocity.y += (e.y-this.y)/100;
			// this.velocity.x += (e.x-this.x)/100;
		}

		this.x += this.velocity.x;
		this.y += this.velocity.y;
		// velocity.y -= (y-100)/100.0;// gravitation
		// velocity.x -= (x-100)/100.0;
		velocity.y += -0.5;

		this.direction += this.angular_velocity;
		while (direction > 2 * Math.PI) {
			direction -= 2 * Math.PI;
		}
	}

	private void updateDisplay() {
		if (!exists) return;
		int image_width = image.getWidth();
		int image_height = image.getHeight();

		for (double x = -width / 2; x < width / 2; x++) {
			for (double y = -height / 2; y < width / 2; y++) {
				Point v = new Point(x, y);
				v.getPolar();
				v.y += this.direction;

				while (v.y > 2 * Math.PI) {
					v.y -= 2 * Math.PI;
				}

				v.getUnitVectors();
				v.x += this.x;
				v.y += this.y;

				Color color = this.color;
				double i_x = v.x + image_width / 2;
				double i_y = image_height / 2 - 1 - v.y;

				int i_x_floor = (int) Math.floor(i_x);
				int i_x_ceil = (int) Math.ceil(i_x);
				int i_y_floor = (int) Math.floor(i_y);
				int i_y_ceil = (int) Math.ceil(i_y);

				try {
					paintPoint(i_x_floor, i_y_floor, color);
					paintPoint(i_x_ceil, i_y_ceil, color);

					paintPoint(i_x_floor - 1, i_y_floor, color);
					paintPoint(i_x_floor, i_y_floor - 1, color);
					paintPoint(i_x_ceil + 1, i_y_ceil, color);
					paintPoint(i_x_ceil, i_y_ceil + 1, color);
				} catch (ArrayIndexOutOfBoundsException e) {
					// if it is impossible for a pixel of the Entity to be
					// on the screen, do not render it
					double corner_max = Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
					if (this.x + corner_max < -image_width / 2 || this.x - corner_max > image_width / 2) {
						exists = false;
					}
					if (this.y + corner_max < -image_height / 2 || this.y - corner_max > image_height / 2) {
						exists = false;
					}
				}
			}
		}
	}

	public void paintPoint(int x, int y, Color c) {
		try {
			if (image.getRGB(x, y) != new Color(255, 255, 255).getRGB() && image.getRGB(x, y) != color.getRGB()) {
				image.setRGB(x, y, new Color(0, 0, 0).getRGB());
			} else {
				image.setRGB(x, y, color.getRGB());
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
}
