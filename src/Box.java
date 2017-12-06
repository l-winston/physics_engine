import java.awt.Color;
import java.awt.image.BufferedImage;

public class Box extends Entity {
	public double height;
	public double width;

	public Box(double height, double width, Vector velocity, double x, double y, double mass, Color color,
			double direction, double angular_velocity, boolean exists) {
		this.height = height;
		this.width = width;
		this.velocity = new Vector(velocity.x, velocity.y);
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.color = color;
		this.direction = direction;
		this.angular_velocity = angular_velocity;
		this.exists = exists;
	}

	public void update() {
		BufferedImage image = PhysicsMain.image;
		int image_width = image.getWidth();
		int image_height = image.getHeight();

		if (exists) {
			for (double x = -width / 2; x < width / 2; x++) {
				for (double y = -height / 2; y < width / 2; y++) {
					Vector v = new Vector(x, y);
					v.getPolar();
					v.y += this.direction;

					while (v.y > 2 * Math.PI) {
						v.y -= 2 * Math.PI;
					}

					v.getUnitVectors();
					v.x += this.x;
					v.y += this.y;
					Color color = this.color;
					try {
						double i_x = v.x + image_width / 2;
						double i_y = image_height / 2 - 1 - v.y;

						int i_x_floor = (int) Math.floor(i_x);
						int i_x_ceil = (int) Math.ceil(i_x);
						int i_y_floor = (int) Math.floor(i_y);
						int i_y_ceil = (int) Math.ceil(i_y);

						if (image.getRGB(i_x_floor, i_y_floor) != new Color(255, 255, 255).getRGB()
								&& image.getRGB(i_x_floor, i_y_floor) != color.getRGB()) {
							image.setRGB(i_x_floor, i_y_floor, new Color(0, 0, 0).getRGB());
						} else {
							image.setRGB(i_x_floor, i_y_floor, color.getRGB());
						}

						if (image.getRGB(i_x_ceil, i_y_ceil) != color.getRGB()
								&& image.getRGB(i_x_ceil, i_y_ceil) != new Color(255, 255, 255).getRGB()) {
							image.setRGB(i_x_ceil, i_y_ceil, new Color(0, 0, 0).getRGB());
						} else {
							image.setRGB(i_x_ceil, i_y_ceil, color.getRGB());
						}
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
		this.x += velocity.x;
		this.y += velocity.y;
		// velocity.y -= (y-0)/1000.0;// gravitation
		// velocity.x -= (x-0)/1000.0;
		velocity.y -= 0.1;
		this.direction += this.angular_velocity;
		while (direction > 2 * Math.PI) {
			direction -= 2 * Math.PI;
		}
	}

}
