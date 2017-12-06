import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Box extends Entity {
	public double height;
	public double width;

	public Box(double height, double width, Vector velocity, double x, double y, double mass, Color color, double direction) {
		this.height = height;
		this.width = width;
		this.velocity = new Vector(velocity.x, velocity.y);
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.color = color;
		this.direction = direction;
	}

	public void update() {
		while(direction > 2*Math.PI) {
			direction -= 2*Math.PI;
		}
		
		BufferedImage image = PhysicsMain.image;
		int image_width = image.getWidth();
		int image_height = image.getHeight();
		
		for (double x = -width / 2; x < width / 2; x++) {
			for (double y = -height / 2; y < width / 2; y++) {
				Vector v = new Vector(x, y);
				v.getPolar();
				v.y += this.direction;
				while(v.y > 2*Math.PI) {
					v.y -= 2*Math.PI;
				}
				v.getUnitVectors();
				v.x += this.x;
				v.y += this.y; 
				image.setRGB((int)Math.ceil(v.x + image_width/2), (int) Math.ceil(image_height/2 - 1 - v.y), color.getRGB());
				image.setRGB((int)Math.floor(v.x + image_width/2), (int) Math.floor(image_height/2 - 1 - v.y), color.getRGB());
				
			}
		}

//		for (int x = -image_width / 2; x < image_width / 2; x++) {
//			for (int y = -image_height / 2; y < image_height / 2; y++) {
//				if (x < this.x + width / 2 && x > this.x - width / 2 && y < this.y + height / 2
//						&& y > this.y - height / 2) {
//
//					double cx = x + image_width / 2.0;
//					double cy = image_height / 2.0 - 1 - y;
//
//					image.setRGB((int) Math.round(cx), (int) Math.round(cy), color.getRGB());
//				}
//			}
//		}

		this.x += velocity.x;
		this.y += velocity.y;
		//velocity.y -= (y-0)/1000.0;// gravitation
		//velocity.x -= (x-0)/1000.0;


	}

}
