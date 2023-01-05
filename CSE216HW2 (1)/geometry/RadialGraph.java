package geometry;

import java.util.*;

public class RadialGraph extends Shape {

    private Point center;
    private List<Point> neighbors;
    protected List<Point> getNeighbors(){
        return this.neighbors;
    }
    private static double toRadians(double degrees){
        return Math.toRadians(degrees);
    }
    public RadialGraph(Point center, List<Point> neighbors) {
        // TODO: part of assignment
        // Make sure it catches uneven sides and it accepts centers.
        if(neighbors != null){
            double distance = RadialGraph.getDistance(center,neighbors.get(0));

            for(Point vertices : neighbors){
                if(!radialSort(neighbors.toArray(new Point[0]), center)){
                    throw new IllegalArgumentException("Unequal Sides");
                }
            }
        }

        this.center = center;

    }

    private double getAngle(Point one){
        double angle = Math.atan2(one.getY() , one.getX()) * 180 / Math.PI;
        if(angle < 0)
            return angle + 360;
        else
            return angle;
    }

    private boolean radialSort(Point[] vertices,Point center){
        //First organize the points in a counterclockwise fashion (check if the next point has less of an x and equal y, then if next has greater y more less x.
        //Then check for proper qualities such as sides are the same size.

        for(int i = 0; i<vertices.length; i++){
            for(int j = 0; j< vertices.length - 1; j++) {
                if (getAngle(vertices[j]) > getAngle(vertices[j+1])) {
                    Point holder = vertices[j];
                    vertices[j] = vertices[j + 1];
                    vertices[j + 1] = holder;
                }
            }
        }

        double distanceToCheck = getDistance(center,vertices[0]);

        for (Point vertex : vertices) {
            if (getDistance(center, vertex) != distanceToCheck){
                return false;
            }
        }
        this.neighbors = new ArrayList<>(Arrays.asList(vertices));
        return true;
    }


    private static double getDistance(Point point1, Point point2){
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(),2) + Math.pow(point2.getY()- point1.getY(),2));
    }
    public RadialGraph(Point center) {
        // TODO: part of assignment
        this.center = center;
        this.neighbors = null;
    }

    @Override
    public RadialGraph rotateBy(int degrees) {
        double radians = toRadians(degrees);

        if (this.center.equals(new Point("center", 0, 0))) {
            double x = 0;
            double y = 0;

            List<Point> rotatedNeighbors = new ArrayList<Point>();
            for (Point vertices : neighbors) {

                double rotatedX = (vertices.getX() * Math.cos(radians)) - (vertices.getY() * Math.sin(radians));
                double rotatedY = (vertices.getX() * Math.sin(radians)) + (vertices.getY() * Math.cos(radians));

                rotatedNeighbors.add(new Point(vertices.getName(), Math.round(rotatedX * 100) / 100.0, Math.round(rotatedY * 100) / 100.0));


            }
            return new RadialGraph(this.center, rotatedNeighbors);

        } else {
            double x = 0;
            double y = 0;

            List<Point> rotatedNeighbors = new ArrayList<Point>();

            for (Point vertices : neighbors) {

                double rotatedX = ((vertices.getX() - this.center.getX()) * Math.cos(radians)) - (((vertices.getY() - this.center.getY()) * Math.sin(radians)) + this.center.getX());
                double rotatedY = ((vertices.getX() - this.center.getX()) * Math.sin(radians)) + (((vertices.getY() - this.center.getY()) * Math.cos(radians)) + this.center.getY());

                rotatedNeighbors.add(new Point(vertices.getName(), Math.round(rotatedX * 100) / 100.0, Math.round(rotatedY * 100) / 100.0));

            }
            return new RadialGraph(this.center, rotatedNeighbors);

            // TODO: part of assignment
        }
    }
    @Override
    public RadialGraph translateBy(double x, double y) {
        Point newCenter = new Point("center",this.center.getX() + x,this.center.getY() + y);
        List<Point> translatedNeighbors = new ArrayList<Point>();
        for(Point point : this.neighbors){
            translatedNeighbors.add(new Point(point.getName(), point.getX() + x, point.getY() + y));
        }

        return new RadialGraph(newCenter,translatedNeighbors); // TODO: part of assignment
    }

    @Override
    public String toString() {
        if(this.neighbors == null){
            return "[" + this.center.toString() + "]";
        } else {
            String accum = "[" + this.center.toString() + "";
            for(Point point : this.neighbors){
                accum +=  "" + "; " + point.toString() ;
            }
            return accum + "]";
        }
        // TODO: part of assignment
    }

    @Override
    public Point center() {
        return this.center; // TODO: part of assignment
    }

    /* Driver method given to you as an outline for testing your code. You can modify this as you want, but please keep
     * in mind that the lines already provided here as expected to work exactly as they are (some lines have additional
     * explanation of what is expected) */
    public static void main(String... args) {
        Point center = new Point("center", 0, 0);
        Point east = new Point("east", 1, 0);
        Point west = new Point("west", -1, 0);
        Point north = new Point("north", 0, 1);
        Point south = new Point("south", 0, -1);
        Point toofarsouth = new Point("south", 0, -2);

        // A single node is a valid radial graph.
        RadialGraph lonely = new RadialGraph(center);

        // Must print: [(center, 0.0, 0.0)]
        System.out.println(lonely);


        // This line must throw IllegalArgumentException, since the edges will not be of the same length
        //RadialGraph nope = new RadialGraph(center, Arrays.asList(north, toofarsouth, east, west));

        Shape g = new RadialGraph(center, Arrays.asList(north, south, east, west));

        // Must follow the documentation in the Shape abstract class, and print the following string:
        // [(center, 0.0, 0.0); (east, 1.0, 0.0); (north, 0.0, 1.0); (west, -1.0, 0.0); (south, 0.0, -1.0)]
        System.out.println(g);

        // After this counterclockwise rotation by 90 degrees, "north" must be at (-1, 0), and similarly for all the
        // other radial points. The center, however, must remain exactly where it was.
        g = g.rotateBy(90);
        System.out.println(g);

        System.out.println(g.center());
        System.out.println(g.translateBy(-3,1));
        // you should similarly add tests for the translateBy(x, y) method

        testSquareClass();
    }

    private static void testSquareClass(){
        Point east = new Point("east", 1, 0);
        Point west = new Point("west", -1, 0);
        Point north = new Point("north", 0, 1);
        Point south = new Point("south", 0, -1);

       Square a = new Square(east,south,west,north);


       Square c = new Square(north,west,south,east);


        System.out.println(a);

        a.rotateBy(90);

        east = new Point("east", 14, 5.75);
        west = new Point("west", 12, 5.25);
        north = new Point("north", 12.75, 6.5);
        south = new Point("south", 13.25, 4.5);

        Square b = new Square(east,south,west,north);


    }
}



