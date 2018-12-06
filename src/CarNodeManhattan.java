import jbotsim.Color;
import jbotsim.Message;
import jbotsim.Node;
import jbotsim.Point;

import java.util.LinkedList;
import java.util.Queue;



public class CarNodeManhattan extends Node{

    enum Sens{
        POSITIVE,
        NEGATIVE;
    }

    private Queue<Point> destinations = new LinkedList<Point>();
    private double frontSpeed = -1, speed = 1; // Number of units to be moved in each step.
    private Point breakdown;
    private Sens sense;

    public CarNodeManhattan(){
        setIcon("car.png");
        setSize(15);

        double rand = Math.random();
        double rand2 = Math.random();

        //spawn
        if(rand<0.5) {
            setLocation((int)(rand * 4)*200 + 100, (int)(rand2 * 4)*200 + 100);
            sense = Sens.POSITIVE;
        }
        else{
            setLocation((int)(rand * 4)*200 + 100, (int)(rand2 * 4)*200 + 100);
            sense = Sens.NEGATIVE;
        }

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

        if(rand < 0.5){
            //tout droit
            if(sense == Sens.POSITIVE)
                if(getLocation().getX() - 200 > 0)
                    addDestination(getLocation().getX() - 200, getLocation().getY());
                else {
                    onArrival();
                }
            else
                if(getLocation().getX() + 200 < 1000 )
                    addDestination(getLocation().getX() + 200, getLocation().getY());
                else {
                    onArrival();
                }
        }
        else if(rand < 0.75){
            //à gauche
            if(sense == Sens.POSITIVE)
                if(getLocation().getY() - 200 > 0)
                    addDestination(getLocation().getX(), getLocation().getY() - 200);
                else {
                    onArrival();
                }
            else
                if(getLocation().getY() + 200 < 1000 )
                    addDestination(getLocation().getX(), getLocation().getY() + 200);
                else {
                    onArrival();
                }
        }
        else{
            //à droite
            if(sense == Sens.NEGATIVE)
                if(getLocation().getY() - 200 > 0)
                    addDestination(getLocation().getX(), getLocation().getY() - 200);
                else {
                    onArrival();
                }
            else
            if(getLocation().getY() + 200 < 1000 )
                addDestination(getLocation().getX(), getLocation().getY() + 200);
            else {
                onArrival();
            }

        }

    }
}
