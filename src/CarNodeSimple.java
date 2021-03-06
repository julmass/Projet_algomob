import jbotsim.Color;
import jbotsim.Message;
import jbotsim.Node;
import jbotsim.Point;

import javax.print.attribute.standard.Destination;
import java.util.LinkedList;
import java.util.Queue;

public class CarNodeSimple extends Node {

	enum Location {
		EAST(1500, 100), WEST(0, 150);

		private Point loc;

		Location(double x, double y) {
			this.loc = new Point(x, y);
		}

		public Point getLoc() {
			return loc;
		}

	}

	enum Direction {
		EAST(1500, 150), WEST(0, 100);

		private Point dest;

		Direction(double x, double y) {
			this.dest = new Point(x, y);
		}

		public Point getDest() {
			return dest;
		}

	}

	private Queue<Point> destinations = new LinkedList<Point>();
	private double frontSpeed = -1, speed = 1; // Number of units to be moved in each step.
	private Point breakdown;

	public CarNodeSimple() {
		setIcon("car.png");
		setSize(25);
		double rand = Math.random() * 2;

		if (rand < 1) {
			setLocation(Math.random() * 1500, Location.WEST.getLoc().getY());
			addDestination(Direction.EAST.getDest());
		} else {
			setLocation(Math.random() * 1500, Location.EAST.getLoc().getY());
			addDestination(Direction.WEST.getDest());
		}

		speed = rand + 0.75;

		setSensingRange(60);

	}

	public void addDestination(Point point) {
		destinations.add(point);
	}

	public void addDestination(double x, double y) {
		addDestination(new Point(x, y));
	}

	public Queue getDestination() {
		return destinations;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	@Override
	public void onSensingIn(Node node) {
		// Recuperation de la vitesse de la voiture devant
		if (getDirection() == node.getDirection() && node.getColor() != Color.BLACK)
			frontSpeed = ((CarNodeSimple) node).getSpeed();

		// Detection d'un vehicule en panne + mode alerte
		if (node.getColor() != null && node.getColor().equals(Color.BLACK)) {
			// Contournement
			if (getDirection() == node.getDirection()) {
				// speed = 0;
				Point dest = destinations.remove();
				Point loc = getLocation();
				if (getDirection() == 0) {
					addDestination(loc.getX() + 50, loc.getY() + 25);
					addDestination(loc.getX() + 100, loc.getY());
					addDestination(dest);
				} else {
					addDestination(loc.getX() - 50, loc.getY() - 25);
					addDestination(loc.getX() - 100, loc.getY());
					addDestination(dest);
				}
			}
			// Mode alerte
			breakdown = node.getLocation();
			sendAll(new Message(breakdown));
			setColor(Color.RED);

		}
	}

	@Override
	public void onMessage(Message message) {
		breakdown = (Point) message.getContent();

		if (getColor() == null) {
			setColor(Color.RED);
		}

	}

	@Override
	public void onClock() {
		// Panne aleatoire
//		if (Math.random() * 100 > 99.99) {
//			setSpeed(0);
//		}

		// Couleur noire pour vehicule en panne
		if (speed == 0)
			setColor(Color.BLACK);

		// Envoie des messages alerte
		if (getColor() == Color.RED)
			if (distance(breakdown) > 500)
				setColor(null);
			else
				sendAll(new Message(breakdown));

		// Adoption de la meme vitesse
		if (frontSpeed > 0 && getColor() != Color.BLACK)
			speed = frontSpeed;

		if (destinations.size() != 0) {
			Point desti = destinations.element();
			if (distance(desti) > speed) {
				setDirection(desti);
				move(speed);
			} else {
				setLocation(desti);
				// destination atteinte
				destinations.remove();
				onArrival();
			}
		}
	}

	public void onArrival() {
		if (destinations.size() == 0) {
			if (getLocation().equals(Direction.EAST.getDest())) {
				setLocation(Location.WEST.getLoc());
				addDestination(Direction.EAST.getDest());
			} else {
				setLocation(Location.EAST.getLoc());
				addDestination(Direction.WEST.getDest());
			}
		}

	}
}
