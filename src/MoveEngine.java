import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MoveEngine extends Thread {
	private long timePassed = 0;
	private long curTime = 0;
	private long lastTime = 0;
	private double timeFraction = 0.0;
	private ArrayList<Force> constForces = new ArrayList<Force>();

	public void run() {
		curTime = System.currentTimeMillis();
		initializeConstForces();
		while (PhysicsMain.isRunning) {
			updateTime();
			applyConstForces();
			sumForces();
			moveEnts();
			try {
				sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}

	private void updateTime() {
		lastTime = curTime;
		curTime = System.currentTimeMillis();
		timePassed = (curTime - lastTime);
		timeFraction = (timePassed / 1000.0);
	}

	private void initializeConstForces() {
		constForces.add(new Force(0.0, PhysicsMain.GRAVITY));
	}

	private synchronized void applyConstForces() {
		double xForce = 0, yForce = 0;
		// Find the total Forceeration of all const forces.
		for (int i = 0; i < constForces.size(); i++) {
			xForce += constForces.get(i).ax();
			yForce += constForces.get(i).ay();
		}
		// Apply the sum Forceeration to each entity.
		for (int i = 0; i < PhysicsMain.entities.size(); i++) {
			Entity s = PhysicsMain.entities.get(i);
			s.addForce(new Force(xForce, yForce));
		}
	}

	private synchronized void sumForces() {
		for (int i = 0; i < PhysicsMain.entities.size(); i++) {
			Entity s = PhysicsMain.entities.get(i);
			// Get the sum of all Forceerations acting on object.
			Force theForce = s.sumForce();
			// Apply the resulting change in velocity.
			double vx = s.getVX() + (theForce.ax() * timeFraction);
			double vy = s.getVY() + (theForce.ay() * timeFraction);
			
			Point springForces = s.getSpringForces();
			vx += springForces.getX()*timeFraction;
			vy += springForces.getY()*timeFraction;
			
			s.setVelocity(vx, vy);
			double bearing = s.getBearing() + (s.getRotationalVelocity() * timeFraction) % Math.toRadians(180);
			s.setBearing(bearing);
			
			// Apply drag coefficient
		}
	}

	private synchronized void moveEnts() {
		for (int i = 0; i < PhysicsMain.entities.size(); i++) {
			Entity s = (Entity) PhysicsMain.entities.get(i);
			// Get the initial x and y coords.
			double oldX = s.getX(), oldY = s.getY();
			// Calculate the new x and y coords.
			double newX = oldX + (s.getVX() * timeFraction);
			double newY = oldY + (s.getVY() * timeFraction);
			s.setPosition(newX, newY);
			checkWallCollisions(s);
		}
		checkCollisions();
	}

	private synchronized void checkCollisions() {
		// compare distances between centers (only applicable for balls)
		for (int i = 0; i < PhysicsMain.entities.size() - 1; i++) {
			if (PhysicsMain.entities.get(i) instanceof Ball) {
				Ball s = (Ball) PhysicsMain.entities.get(i);
				Point2D sCenter = s.getCenter();
				for (int j = i + 1; j < PhysicsMain.entities.size(); j++) {
					if (PhysicsMain.entities.get(j) instanceof Ball) {
						// if ball collides with ball
						Ball t = (Ball) PhysicsMain.entities.get(j);
						if (t == null)
							break;
						Point2D tCenter = t.getCenter();
						double distBetween = sCenter.distance(tCenter);
						if (distBetween < (s.getRadius() + t.getRadius()))
							collide(s, t, distBetween);
					} else if (PhysicsMain.entities.get(j) instanceof Box) {
						// if ball collides with box
						// TODO: create circumstances for a collision between a
						// ball and a box
					}
				}
			} else if (PhysicsMain.entities.get(i) instanceof Box) {
				// if box collides with box
				Box s = (Box) PhysicsMain.entities.get(i);
				for (int j = i + 1; j < PhysicsMain.entities.size(); j++) {
					if (PhysicsMain.entities.get(j) instanceof Box) {
						Box t = (Box) PhysicsMain.entities.get(j);
						if (t == null)
							break;
						// TODO: create circumstances for a collision between
						// two boxes
						// collide(s, t);
					}
				}
			}
		}
	}

	private synchronized void collide(Box s, Box t) {
		// TODO: create outcome for a collision between two boxes
	}

	private synchronized void collide(Box s, Ball t) {
		// TODO: create outcome for a collision between a box and a ball
	}

	private synchronized void collide(Ball s, Ball t, double distBetween) {
		// Get the relative x and y dist between them.
		double relX = s.getX() - t.getX();
		double relY = s.getY() - t.getY();
		// Take the arctan to find the collision angle.
		double collisionAngle = Math.atan2(relY, relX);
		if (collisionAngle < 0)
			collisionAngle += 2 * Math.PI;
		// Rotate the coordinate systems for each object's velocity to align
		// with the collision angle. We do this by supplying the collision angle
		// to the vector's rotateCoordinates method.
		Vector2D sVel = s.getVelocityVector(), tVel = t.getVelocityVector();
		sVel.rotateCoordinates(collisionAngle);
		tVel.rotateCoordinates(collisionAngle);
		// In the collision coordinate system, the contact normals lie on the
		// x-axis. Only the velocity values along this axis are affected. We can
		// now apply a simple 1D momentum equation where the new x-velocity of
		// the first object equals a negative times the x-velocity of the
		// second.
		double swap = sVel.x;
		sVel.x = tVel.x;
		tVel.x = swap;
		// Now we need to get the vectors back into normal coordinate space.
		sVel.restoreCoordinates();
		tVel.restoreCoordinates();
		// Give each object its new velocity.
		s.setVelocity(sVel.x * PhysicsMain.BOUNCE, sVel.y * PhysicsMain.BOUNCE);
		t.setVelocity(tVel.x * PhysicsMain.BOUNCE, tVel.y * PhysicsMain.BOUNCE);
		// Back them up in the opposite angle so they are not overlapping.
		double minDist = s.getRadius() + t.getRadius();
		double overlap = minDist - distBetween;
		double toMove = overlap / 2;
		double newX = s.getX() + (toMove * Math.cos(collisionAngle));
		double newY = s.getY() + (toMove * Math.sin(collisionAngle));
		s.setPosition(newX, newY);
		newX = t.getX() - (toMove * Math.cos(collisionAngle));
		newY = t.getY() - (toMove * Math.sin(collisionAngle));
		t.setPosition(newX, newY);
	}

	private synchronized void checkWallCollisions(Entity e) {
		if (e instanceof Ball) {
			Ball s = (Ball) e;
			int maxY = PhysicsMain.Y - s.dimY();
			int maxX = PhysicsMain.X - s.dimX();
			if (s.getY() > maxY) {
				s.setPosition(s.getX(), maxY);
				s.setVelocity(s.getVX(), (s.getVY() * -PhysicsMain.BOUNCE));
			}
			if (s.getX() > maxX) {
				s.setPosition(maxX, s.getY());
				s.setVelocity((s.getVX() * -PhysicsMain.BOUNCE), s.getVY());
			}
			if (s.getX() < 1) {
				s.setPosition(1, s.getY());
				s.setVelocity((s.getVX() * -PhysicsMain.BOUNCE), s.getVY());
			}
			if (s.getY() < 1) {
				s.setPosition(s.getX(), 1);
				s.setVelocity(s.getVX(), (s.getVY() * -PhysicsMain.BOUNCE));
			}
		} else if (e instanceof Box) {
			Box s = (Box) e;
			// height and width (distance between opposite corners)
			double height = s.maxY() - s.minY();
			double width = s.maxX() - s.minX();

			int maxY = (int) (PhysicsMain.down.getBounds().getMaxY() - height / 2);
			int minY = (int) (PhysicsMain.up.getBounds().getMinY() + height / 2);

			int maxX = (int) (PhysicsMain.right.getBounds().getMaxX() - width / 2);
			int minX = (int) (PhysicsMain.left.getBounds().getMinX() + width / 2);
			if (s.getY() > maxY) {
				s.setPosition(s.getX(), maxY);
				s.setVelocity(s.getVX(), (s.getVY() * -PhysicsMain.BOUNCE));
			}
			if (s.getX() > maxX) {
				s.setPosition(maxX, s.getY());
				s.setVelocity((s.getVX() * -PhysicsMain.BOUNCE), s.getVY());

			}
			if (s.getX() < minX) {
				s.setPosition(minX, s.getY());
				s.setVelocity((s.getVX() * -PhysicsMain.BOUNCE), s.getVY());

			}
			if (s.getY() < minY) {
				s.setPosition(s.getX(), minY);
				s.setVelocity(s.getVX(), (s.getVY() * -PhysicsMain.BOUNCE));
			}
			Area r = new Area(s.getRotated());
			r.intersect(PhysicsMain.down);
			if (!r.isEmpty()) {
				// r is the base (where the box intersects the ground)
			}
		}
	}
}
