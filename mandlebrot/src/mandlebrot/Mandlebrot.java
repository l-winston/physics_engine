package mandlebrot;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

//test push
public class Mandlebrot {
	
	public static void main(String[] args){
		new Mandlebrot();
		
	}
	public static final int render_WIDTH = 600;
	public static final int render_HEIGHT = 400;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int X_OFFSET = 100;
	public static final int Y_OFFSET = 0;
	
	public static int ITERATIONS = 1;
	public static float SCALE = 200;
	public static final int MAX_ITERATIONS = 100;
	
	public static JPanel panel = new JPanel();
	public boolean auto; //if true, automatically changes ITERATIONS
	
	private static BufferedImage buffer; //create buffer, then assign to frame
	
	public Mandlebrot(){
		
		buffer = new BufferedImage(render_WIDTH, render_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		//create slider Component
		JSlider iterations = new JSlider(JSlider.HORIZONTAL, 0, MAX_ITERATIONS, 10);
		iterations.setMajorTickSpacing(10);
		iterations.setMinorTickSpacing(5);
		iterations.setPaintTicks(true);
		iterations.setPaintLabels(true);
		iterations.setVisible(true);
		
		JSlider scale = new JSlider(JSlider.HORIZONTAL, 0, 500, 10);
		scale.setMajorTickSpacing(100);
		scale.setMinorTickSpacing(10);
		scale.setPaintTicks(true);
		scale.setPaintLabels(true);
		scale.setVisible(true);
		
		//create Components
		JFrame frame = new JFrame("mandlebrot");//set name
		JButton button = new JButton("Toggle Mode");
		
		JPanel sliderList = new JPanel();
		sliderList.setLayout(new BoxLayout(sliderList, BoxLayout.Y_AXIS));
		
		sliderList.add(iterations);
		sliderList.add(button);
		sliderList.add(scale);
		
		button.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent e) {
		             // this makes sure the button you are pressing is the button variable
		             if(e.getSource() == button) {
		                auto = !auto;
		                if(auto){
		                	button.setText("AUTO");
		                }else{
		                	button.setText("MANUAL");
		                }
		              }
		       }
		 });
		
		//create the Mandlebrot Set on a BufferedImage
		renderMandelbrotSet();
		
		frame.pack();//???
		frame.setVisible(true);
		frame.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit when frame is closed
		frame.setResizable(true);
		
		frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.getContentPane().add(new JLabel(new ImageIcon(buffer)));
		frame.getContentPane().add(sliderList);
		
		while(true){
			int time = 50;
			SCALE = scale.getValue();
			if(!auto){
				ITERATIONS = iterations.getValue();
			}else{
				time = MAX_ITERATIONS/2-ITERATIONS/2; 
				ITERATIONS += 1;
				
				if(ITERATIONS > MAX_ITERATIONS){
					ITERATIONS = 1;
				}
				iterations.setValue(ITERATIONS);
			}
			
			//reset the frame
			frame.getContentPane().invalidate();
			frame.getContentPane().validate();
			
			//redraw the Mandlebrot Set onto a BufferedImage
			renderMandelbrotSet();
			frame.pack();//resizes it to preferred size and layout of subcomponents
			frame.repaint();

			try {
				if(auto){
					Thread.sleep(time);
				}else{
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void renderMandelbrotSet(){
		for(int x = 0; x < render_WIDTH; x++){
			for(int y = 0; y < render_HEIGHT; y++){
				//convert to Cartesian plane coordinates & factor in scaling
				int color = calculatePoint(((x - X_OFFSET)-render_WIDTH/2f)/SCALE, ((y + Y_OFFSET)-render_HEIGHT/2f)/SCALE);
				//assign each pixel its new color
				buffer.setRGB(x, y, color);
			}
		}
	}
	
	public int calculatePoint(float x, float y){
		float cx = x;
		float cy = y;
		int i = 0;
		for(; i < ITERATIONS; i++){
			//squaring a complex number can be interpreted as below where x = a and y = b in (a + bi) 
			float nx = x*x - y*y + cx;
			float ny = 2*x*y + cy;
			x = nx;
			y = ny;
			if(x*x + y*y > 4) break;
		}
		
		//if (x, y) is part of Mandelbrot, color it black
		if(i == ITERATIONS) return 0x000000;//
		//else, make its hue based on how many iterations it took for it to diverge 
		return Color.HSBtoRGB((float) i / ITERATIONS , 0.5f, 1);
	}
	
	//public void paint(Graphics g){
	//	g.drawImage(buffer, 0, 0, null);
	//}
	
	
	
}
