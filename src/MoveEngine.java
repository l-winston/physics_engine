import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MoveEngine extends Thread {
	private long timePassed = 0;
	private long curTime = 0;
	private long lastTime = 0;
	private double timeFraction = 0.0;
	private ArrayList<Accel> constForces = new ArrayList<Accel>();

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
		constForces.add(new Accel(0.0, PhysicsMain.GRAVITY));
	}

	private synchronized void applyConstForces() {
		double xAccel = 0, yAccel = 0;
		// Find the total acceleration of all const forces.
		for (int i = 0; i < constForces.size(); i++) {
			xAccel += constForces.get(i).ax();
			yAccel += constForces.get(i).ay();
		}
		// Apply the sum acceleration to each entity.
		for (int i = 0; i < PhysicsMain.entities.size(); i++) {
			Entity s = PhysicsMain.entities.get(i);
			s.addAccel(new Accel(xAccel, yAccel));
		}
	}

	private synchronized void sumForces() {
		for (int i = 0; i < PhysicsMain.entities.size(); i++) {
			Entity s = PhysicsMain.entities.get(i);
			// Get the sum of all accelerations acting on object.
			Accel theAccel = s.sumAccel();
			// Apply the resulting change in velocity.
			double vx = s.vx() + (theAccel.ax() * timeFraction);
			double vy = s.vy() + (theAccel.ay() * timeFraction);
			
			Point springForces = s.getSpringForces();
			vx += springForces.getX();
			vy += springForces.getY();
			
			s.updateVelocity(vx, vy);
			double bearing = s.bearing() + (s.spin() * timeFraction) % Math.toRadians(180);
			s.updateBearing(bearing);
			// Apply drag coefficient
			s.applyDrag(1.0 - (timeFraction * PhysicsMain.DRAG));
		}
	}

	private synchronized void moveEnts() {
		for (int i = 0; i < PhysicsMain.entities.size(); i++) {
			Entity s = (Entity) PhysicsMain.entities.get(i);
			// Get the initial x and y coords.
			double oldX = s.getX(), oldY = s.getY();
			// Calculate the new x and y coords.
			double newX = oldX + (s.vx() * timeFraction);
			double newY = oldY + (s.vy() * timeFraction);
			s.updatePos(newX, newY);
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
		Vector2D sVel = s.velVector(), tVel = t.velVector();
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
		s.updateVelocity(sVel.x * PhysicsMain.BOUNCE, sVel.y * PhysicsMain.BOUNCE);
		t.updateVelocity(tVel.x * PhysicsMain.BOUNCE, tVel.y * PhysicsMain.BOUNCE);
		// Back them up in the opposite angle so they are not overlapping.
		double minDist = s.getRadius() + t.getRadius();
		double overlap = minDist - distBetween;
		double toMove = overlap / 2;
		double newX = s.getX() + (toMove * Math.cos(collisionAngle));
		double newY = s.getY() + (toMove * Math.sin(collisionAngle));
		s.updatePos(newX, newY);
		newX = t.getX() - (toMove * Math.cos(collisionAngle));
		newY = t.getY() - (toMove * Math.sin(collisionAngle));
		t.updatePos(newX, newY);
	}

	private synchronized void checkWallCollisions(Entity e) {
		if (e instanceof Ball) {
			Ball s = (Ball) e;
			int maxY = PhysicsMain.Y - s.dimY();
			int maxX = PhysicsMain.X - s.dimX();
			if (s.getY() > maxY) {
				s.updatePos(s.getX(), maxY);
				s.updateVelocity(s.vx(), (s.vy() * -PhysicsMain.BOUNCE));
			}
			if (s.getX() > maxX) {
				s.updatePos(maxX, s.getY());
				s.updateVelocity((s.vx() * -PhysicsMain.BOUNCE), s.vy());
			}
			if (s.getX() < 1) {
				s.updatePos(1, s.getY());
				s.updateVelocity((s.vx() * -PhysicsMain.BOUNCE), s.vy());
			}
			if (s.getY() < 1) {
				s.updatePos(s.getX(), 1);
				s.updateVelocity(s.vx(), (s.vy() * -PhysicsMain.BOUNCE));
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
				s.updatePos(s.getX(), maxY);
				s.updateVelocity(s.vx(), (s.vy() * -PhysicsMain.BOUNCE));
				s.updateSpin(s.spin() * -PhysicsMain.BOUNCE);
			}
			if (s.getX() > maxX) {
				s.updatePos(maxX, s.getY());
				s.updateVelocity((s.vx() * -PhysicsMain.BOUNCE), s.vy());
				s.updateSpin(s.spin() * -PhysicsMain.BOUNCE);

			}
			if (s.getX() < minX) {
				s.updatePos(minX, s.getY());
				s.updateVelocity((s.vx() * -PhysicsMain.BOUNCE), s.vy());
				s.updateSpin(s.spin() * -PhysicsMain.BOUNCE);

			}
			if (s.getY() < minY) {
				s.updatePos(s.getX(), minY);
				s.updateVelocity(s.vx(), (s.vy() * -PhysicsMain.BOUNCE));
				s.updateSpin(s.spin() * -PhysicsMain.BOUNCE);
			}
			Area r = new Area(s.getRotated());
			r.intersect(PhysicsMain.down);
			if (!r.isEmpty()) {
				// r is the base (where the box intersects the ground)
			}
		}
	}
}
