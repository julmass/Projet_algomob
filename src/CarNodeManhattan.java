import jbotsim.Color;
import jbotsim.Message;
import jbotsim.Node;
import jbotsim.Point;

import java.util.LinkedList;
import java.util.Queue;

public class CarNodeManhattan extends Node {

	enum Orientation {
		SOUTH, NORTH, WEST, EAST;
	}

	private Queue<Point> destinations = new LinkedList<Point>();
	private double frontSpeed = -1, speed = 1; // Number of units to be moved in each step.
	private CarNodeManhattan breakdown;
	private Orientation orien;

	public CarNodeManhattan() {
		setIcon("car.png");
		setSize(20);

		double rand = Math.random();
		double rand2 = Math.random();

		// spawn
		setLocation((int) (rand * 5) * 200 + 100, (int) (rand2 * 5) * 200 + 100);
		orien = Orientation.values()[(int) rand * 4];
		onArrival();

		speed = rand + 0.75;

		setSensingRange(40);

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

	public Orientation getOrientation() {
		return orien;
	}

	@Override
	public void onSensingIn(Node node) {
		// Recuperation de la vitesse de la voiture devant
		if (getDirection() == node.getDirection() && node.getColor() != Color.BLACK)
			frontSpeed = ((CarNodeManhattan) node).getSpeed();

		// Detection d'un vehicule en panne
		if (node.getColor() != null && node.getColor().equals(Color.BLACK)) {
			// Si le vehicule en panne est dans la même direction, faire un demi-tour (Si la
			// voiture n'est pas encore au courant d'une panne sinon elle contourne la rue
			// dans la fonction onArrival())
			if (getDirection() == node.getDirection()) {
				Point dest = destinations.remove();
				switch (orien) {
				case NORTH:
					orien = Orientation.SOUTH;
					addDestination(dest.getX(), dest.getY() + 200);
					break;
				case SOUTH:
					orien = Orientation.NORTH;
					addDestination(dest.getX(), dest.getY() - 200);
					break;
				case WEST:
					orien = Orientation.EAST;
					addDestination(dest.getX() + 200, dest.getY());
					break;
				case EAST:
					orien = Orientation.WEST;
					addDestination(dest.getX() - 200, dest.getY());
					break;
				}
			}
			// Mode alerte
			breakdown = (CarNodeManhattan) node;
			sendAll(new Message(breakdown));
			setColor(Color.RED);

		}
	}

	@Override
	public void onMessage(Message message) {
		breakdown = (CarNodeManhattan) message.getContent();

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
			if (distance(breakdown) > 400)
				setColor(null);
			else
				sendAll(new Message(breakdown));

		// Adoption de la meme vitesse
		if (frontSpeed >= 0 && getColor() != Color.BLACK)
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
		double rand = Math.random();

		switch (orien) {
		case NORTH:
			if (rand < 0.5)
				// tout droit
				if (getLocation().getY() - 200 > 0)
					addDestination(getLocation().getX(), getLocation().getY() - 200);
				else
					onArrival();
			else if (rand < 0.75)
				// à gauche
				if (getLocation().getX() - 200 > 0) {
					addDestination(getLocation().getX() - 200, getLocation().getY());
					orien = Orientation.WEST;
				} else
					onArrival();
			else
			// à droite
			if (getLocation().getX() + 200 < 1000) {
				addDestination(getLocation().getX() + 200, getLocation().getY());
				orien = Orientation.EAST;
			} else
				onArrival();
			break;

		case SOUTH:
			if (rand < 0.5)
				// tout droit
				if (getLocation().getY() + 200 < 1000)
					addDestination(getLocation().getX(), getLocation().getY() + 200);
				else
					onArrival();
			else if (rand < 0.75)
				// à gauche
				if (getLocation().getX() + 200 < 1000) {
					addDestination(getLocation().getX() + 200, getLocation().getY());
					orien = Orientation.EAST;
				} else
					onArrival();
			else
			// à droite
			if (getLocation().getX() - 200 > 0) {
				addDestination(getLocation().getX() - 200, getLocation().getY());
				orien = Orientation.WEST;
			} else
				onArrival();
			break;

		case WEST:
			if (rand < 0.5)
				// tout droit
				if (getLocation().getX() - 200 > 0)
					addDestination(getLocation().getX() - 200, getLocation().getY());
				else
					onArrival();
			else if (rand < 0.75)
				// à gauche
				if (getLocation().getY() + 200 < 1000) {
					addDestination(getLocation().getX(), getLocation().getY() + 200);
					orien = Orientation.SOUTH;
				} else
					onArrival();
			else
			// à droite
			if (getLocation().getY() - 200 > 0) {
				addDestination(getLocation().getX(), getLocation().getY() - 200);
				orien = Orientation.NORTH;
			} else
				onArrival();
			break;

		case EAST:
			if (rand < 0.5)
				// tout droit
				if (getLocation().getX() + 200 < 1000)
					addDestination(getLocation().getX() + 200, getLocation().getY());
				else
					onArrival();
			else if (rand < 0.75)
				// à gauche
				if (getLocation().getY() - 200 > 0) {
					addDestination(getLocation().getX(), getLocation().getY() - 200);
					orien = Orientation.NORTH;
				} else
					onArrival();
			else
			// à droite
			if (getLocation().getY() + 200 < 1000) {
				addDestination(getLocation().getX(), getLocation().getY() + 200);
				orien = Orientation.SOUTH;
			} else
				onArrival();
			break;
		}

		// Contournement si voiture en panne dans la rue
		if (breakdown != null && breakdown.getOrientation() == orien && distance(breakdown) < 200) {
			Point dest = destinations.remove();
			onArrival();
		}

	}
}
