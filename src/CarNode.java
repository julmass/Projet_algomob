import jbotsim.Color;
import jbotsim.Message;
import jbotsim.Node;
import jbotsim.Point;

import javax.print.attribute.standard.Destination;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jmassonneau on 29/11/18.
 */
enum Location{
    EAST(1490,100),
    WEST(10,150);

    private Point loc;

    Location(double x, double y){
        this.loc = new Point(x, y);
    }

    public Point getLoc(){
        return loc;
    }

}

 enum Direction{
        EAST(1490,150),
        WEST(10,100);

    private Point dest;

    Direction(double x, double y){
        this.dest = new Point(x,y);
    }

    public Point getDest(){
        return dest;
    }

}
public class CarNode extends Node {
    private Queue<Point> destinations = new LinkedList<Point>();
    private double speed = 1; // Number of units to be moved in each step.

    public CarNode(){
        setIcon("car.png");
        setSize(25);
        double rand = Math.random()*2;

        if(rand<1) {
            setLocation(Math.random()*1500,Location.WEST.getLoc().getY());
            addDestination(Direction.EAST.getDest());
        }
        else{
            setLocation(Math.random()*1500, Location.EAST.getLoc().getY());
            addDestination(Direction.WEST.getDest());
        }

        setSpeed(rand+0.5);

        setSensingRange(60);
    }

    public void addDestination(Point point) {
        destinations.add(point);
    }

    public void addDestination(double x, double y) {
        addDestination(new Point(x,y));
    }

    public Queue getDestination(){
        return destinations;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void onSensingIn(Node node){
        if(node.getColor()!=null && node.getColor().equals(Color.BLACK) ){
            if(getDirection() == node.getDirection())
                setSpeed(0);
            else if(getColor()==null){
                alertMode();
            }


        }
    }

    public void alertMode(){
        setSpeed(speed/2);
        sendAll(new Message());
        setColor(Color.RED);
    }

    @Override
    public void onMessage(Message message){
        if(getColor()==null)
            alertMode();
    }

    @Override
    public void onClock() {
        // Panne aleatoire
        /*if(Math.random()*100>99.95) {
            setSpeed(0);
        }*/

        if(speed==0)
            setColor(Color.BLACK);

        if(destinations.size() != 0) {
            Point desti = destinations.element();
            if(distance(desti) > speed) {
                setDirection(desti);
                move(speed);
            }
            else {
                setLocation(desti);
                // destination atteinte
                destinations.remove();
                onArrival();
            }
        }
    }

    public void onArrival() {
        if(getLocation().equals(Direction.EAST.getDest())){
            setLocation(Location.WEST.getLoc());
            addDestination(Direction.EAST.getDest());
        }
        else {
            setLocation(Location.EAST.getLoc());
            addDestination(Direction.WEST.getDest());
        }

    }
}
