import jbotsim.Color;
import jbotsim.Message;
import jbotsim.Node;
import jbotsim.Point;

import java.util.LinkedList;
import java.util.Queue;



public class CarNodeManhattan extends Node{

    enum Orientation{
        SOUTH,
        NORTH,
        WEST,
        EAST;
    }

    private Queue<Point> destinations = new LinkedList<Point>();
    private double frontSpeed = -1, speed = 1; // Number of units to be moved in each step.
    private Point breakdown;
    private Orientation orien;

    public CarNodeManhattan(){
        setIcon("car.png");
        setSize(15);

        double rand = Math.random();
        double rand2 = Math.random();

        //spawn
        if(rand<0.5) {
            setLocation((int)(rand * 5)*200 + 100, (int)(rand2 * 5)*200 + 100);
        }
        else{
            setLocation((int)(rand * 5)*200 + 100, (int)(rand2 * 5)*200 + 100);
        }

        orien = Orientation.values()[(int) rand*4];
        onArrival();




        speed = rand+0.75;

        setSensingRange(40);

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

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getSpeed(){
        return speed;
    }



    @Override
    public void onSensingIn(Node node){

    }

    @Override
    public void onMessage(Message message){


    }

    @Override
    public void onClock() {


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
        double rand = Math.random();

        switch(orien){
            case NORTH:
                if(rand<0.5)
                    //tout droit
                    if(getLocation().getX() - 200 > 0)
                        addDestination(getLocation().getX() - 200, getLocation().getY());
                    else
                        onArrival();
                else if(rand < 0.75)
                    //à gauche
                        if(getLocation().getY() - 200 > 0) {
                            addDestination(getLocation().getX(), getLocation().getY() - 200);
                            orien = Orientation.WEST;
                        }
                        else
                            onArrival();
                else
                    //à droite
                    if(getLocation().getY() + 200 < 1000) {
                        addDestination(getLocation().getX(), getLocation().getY() + 200);
                        orien = Orientation.EAST;
                    }
                    else
                        onArrival();
                break;

            case SOUTH:
                if(rand<0.5)
                    //tout droit
                    if(getLocation().getX() + 200 < 1000)
                        addDestination(getLocation().getX() + 200, getLocation().getY());
                    else
                        onArrival();
                else if(rand < 0.75)
                    //à gauche
                    if(getLocation().getY() + 200 < 1000) {
                        addDestination(getLocation().getX(), getLocation().getY() + 200);
                        orien = Orientation.EAST;
                    }
                    else
                        onArrival();
                else
                    //à droite
                    if(getLocation().getY() - 200 > 0) {
                        addDestination(getLocation().getX(), getLocation().getY() - 200);
                        orien = Orientation.WEST;
                    }
                    else
                        onArrival();
                break;

            case WEST:
                if(rand<0.5)
                    //tout droit
                    if(getLocation().getY() - 200 > 0)
                        addDestination(getLocation().getX(), getLocation().getY() - 200);
                    else
                        onArrival();
                else if(rand < 0.75)
                    //à gauche
                    if(getLocation().getX() + 200 < 1000){
                        addDestination(getLocation().getX() + 200, getLocation().getY());
                        orien = Orientation.SOUTH;
                    }
                    else
                        onArrival();
                else
                    //à droite
                    if(getLocation().getX() - 200 > 0){
                        addDestination(getLocation().getX() - 200, getLocation().getY());
                        orien = Orientation.NORTH;
                    }
                    else
                        onArrival();
                break;

            case EAST:
                if(rand<0.5)
                    //tout droit
                    if(getLocation().getY() + 200 < 1000)
                        addDestination(getLocation().getX(), getLocation().getY() + 200);
                    else
                        onArrival();
                else if(rand < 0.75)
                    //à gauche
                    if(getLocation().getX() - 200 > 0){
                        addDestination(getLocation().getX() - 200, getLocation().getY());
                        orien = Orientation.NORTH;
                    }
                    else
                        onArrival();
                else
                    //à droite
                    if(getLocation().getX() + 200 < 1000){
                        addDestination(getLocation().getX() + 200, getLocation().getY());
                        orien = Orientation.SOUTH;
                    }
                    else
                        onArrival();
                break;
        }

    }
}
