import java.awt.Color;
import java.awt.image.BufferedImage;

public class Box extends Entity {
	public static double height;
	public static double width;

	public Box(double height, double width, double speed, double direction, double x, double y, double mass, Color color) {
		this.height = height;
		this.width = width;
		this.speed = speed;
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.color = color;
	}
	
	public void update(){		
		BufferedImage image = PhysicsMain.image;
		int image_width = image.getWidth();
		int image_height = image.getHeight();
		
		for(int x = -image_width/2; x < image_width/2; x++){
			for(int y = -image_height/2; y < image_height/2; y++){
				if(x < this.x+width/2 && x > this.x-width/2 && y < this.x+height/2 && y > this.x-height/2){
					System.out.println(x + " " + y);

					double cx = x+image_width/2.0;
					double cy = image_height/2.0 - 1 - y;
					System.out.println(cx + " " + cy);

					image.setRGB((int) Math.round(cx), (int) Math.round(cy), color.getRGB());
				}
			}
		}
	}

}
