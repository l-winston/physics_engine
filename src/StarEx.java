import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setPaint(Color.gray);
		g2d.translate(300, 250);


		Polygon rect = new Polygon();
		rect.addPoint(100, 100);
		rect.addPoint(100, -100);
		rect.addPoint(-100, -100);
		rect.addPoint(-100, 100);
		
		Rectangle bounds = rect.getBounds();
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(50), bounds.width / 2, bounds.height / 2);
		
		g2d.fill(transform.createTransformedShape(rect));
		g2d.dispose();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}
}

public class StarEx extends JFrame {

	public StarEx() {

		add(new Surface());

		setTitle("Star");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				StarEx ex = new StarEx();
				ex.setVisible(true);
			}
		});
	}
}