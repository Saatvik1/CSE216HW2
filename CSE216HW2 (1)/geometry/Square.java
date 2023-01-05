package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Square extends Shape {
    private Point center;

    private Point[] vertices = new Point[4];

    public Point[] getVertices(){
        return this.vertices;
    }
    public Square(Point a, Point b, Point c, Point d) {
        Point[] toCheck = {a,b,c,d};

        //isSquare(toCheck);
        if(!isSquare(toCheck))
            throw new IllegalArgumentException("Incorrect square parameters.");

        this.center = new Point("center", ((this.vertices[0].getX() + this.vertices[2].getX()) / 2), ((this.vertices[1].getY() + this.vertices[3].getY()) / 2));

        // TODO: part of the assignment
    }

    private static double getDistance(Point point1, Point point2){
        return Math.round(Math.sqrt(Math.pow(point2.getX() - point1.getX(),2) + Math.pow(point2.getY()- point1.getY(),2)) * 100)/100.0;
    }
    private boolean isSquare(Point[] vertices){
        //First organize the points in a counterclockwise fashion (check if the next point has less of an x and equal y, then if next has greater y more less x.
        //Then check for proper qualities such as sides are the same size.

        for(int i = 0; i<vertices.length; i++){
            for(int j = 0; j< vertices.length - 1; j++) {
                if (vertices[j].getX() + vertices[j].getY() < vertices[j + 1].getX() + vertices[j + 1].getY()) {
                    Point holder = vertices[j];
                    vertices[j] = vertices[j + 1];
                    vertices[j + 1] = holder;
                }
            }
        }

        if(!checkSquareVerticesHelper(vertices)){
            if (vertices[1].getX() > vertices[2].getX()) {
                Point holder = vertices[1];
                vertices[1] = vertices[2];
                vertices[2] = holder;
            }
        }



        if(!checkSquareVerticesHelper(vertices)) {
            if (vertices[2].getY() < vertices[3].getY()) {
                Point holder = vertices[2];
                vertices[2] = vertices[3];
                vertices[3] = holder;
            }
        }

        if(!checkSquareVerticesHelper(vertices)) {
            if (vertices[2].getX() > vertices[3].getX()) {
                Point holder = vertices[2];
                vertices[2] = vertices[3];
                vertices[3] = holder;
            }
        }

        if(!checkSquareVerticesHelper(vertices)) {
            if (vertices[0].getX() + vertices[0].getY() == vertices[1].getX() + vertices[1].getY()) {
                if (vertices[0].getY() > vertices[1].getY()) {
                    Point holder = vertices[0];
                    vertices[0] = vertices[1];
                    vertices[1] = holder;
                }
            }
        }

        if((getDistance(vertices[0], vertices[1]) == getDistance(vertices[1], vertices[2])) && (getDistance(vertices[2], vertices[3]) == getDistance(vertices[3], vertices[0]))){
            this.vertices = vertices;
            return true;
        }


        return false;
    }

    private boolean checkSquareVerticesHelper(Point[] vertices){
        if((getDistance(vertices[0], vertices[1]) == getDistance(vertices[1], vertices[2])) && (getDistance(vertices[2], vertices[3]) == getDistance(vertices[3], vertices[0]))){
            this.vertices = vertices;
            return true;
        }
        return false;
    }
    @Override
    public Point center() {
        return this.center; // TODO: part of the assignment
    }

    private static double toRadians(double degrees){
        return Math.toRadians(degrees);
    }

    @Override
    public Square rotateBy(int degrees) {
        double radians = toRadians(degrees);

        if (this.center.equals(new Point("center", 0, 0))) {

            Point[] rotatedVertices = new Point[4];
            int i = 0;
            for (Point vertices : this.vertices) {

                double rotatedX = (vertices.getX() * Math.cos(radians)) - (vertices.getY() * Math.sin(radians));
                double rotatedY = (vertices.getX() * Math.sin(radians)) + (vertices.getY() * Math.cos(radians));

                rotatedVertices[i++] = new Point(vertices.getName(), Math.round(rotatedX * 100)/100.0, Math.round(rotatedY * 100)/100.0); //IDK IF I CAN USE MATH.ROUND HERE, but it has the correct values.
            }


            return new Square(rotatedVertices[0],rotatedVertices[1],rotatedVertices[2],rotatedVertices[3]);

        } else {

            Point[] rotatedVertices = new Point[4];
            int i = 0;

            for (Point vertices : this.vertices) {

                double rotatedX = ((vertices.getX() - this.center.getX()) * Math.cos(radians)) - (((vertices.getY() - this.center.getY()) * Math.sin(radians)) ) + this.center.getX();
                double rotatedY = ((vertices.getX() - this.center.getX()) * Math.sin(radians)) + (((vertices.getY() - this.center.getY()) * Math.cos(radians)) ) + this.center.getY();

                rotatedVertices[i++] = new Point(vertices.getName(), Math.round(rotatedX * 100) /100.0, Math.round(rotatedY * 100)/100.0);

            }

            return new Square(rotatedVertices[0],rotatedVertices[1],rotatedVertices[2],rotatedVertices[3]);

            // TODO: part of assignment
        }
    }


    @Override
    public Square translateBy(double x, double y) {
        Point[] translatedVertices = new Point[4];
        for(int i = 0; i<4; i++){
            translatedVertices[i] = new Point(this.vertices[i].getName(),this.vertices[i].getX() + x,this.vertices[i].getY() + y);
        }
        return new Square(translatedVertices[0],translatedVertices[1],translatedVertices[2],translatedVertices[3]);
    }


    @Override
    public String toString() { //If I need to change the order of the way it is printed, Copy the angle sorting method into here since it should be a valid square already.
        if(this.center.equals(new Point(this.center.getName(),0,0))){
            if(this.vertices == null){
                return "[" + this.center.toString() + "]";
            } else {

                String accum = "[";
                for(Point point : this.vertices){
                    accum += point + "; ";
                }

                return accum.substring(0,accum.length()-2) + "]";
            }
        } else {
            double xTranslate = this.center.getX() * -1;
            double yTranslate = this.center.getY() * -1;

            Point[] correctOrder = squareSort(this.translateBy(xTranslate,yTranslate).getVertices());
            Point[] shiftedCorrectOrder = new Point[4];

            int i = 0;


            for(Point point : correctOrder){
                shiftedCorrectOrder[i++] = new Point(point.getName(), point.getX() + (xTranslate * -1) ,point.getY() + (yTranslate * -1));
            }
            String accum = "[";

            for(Point point : shiftedCorrectOrder){
                accum += point + "; ";
            }

            return accum.substring(0,accum.length()-2) + "]";

        }
         // TODO: part of the assignment
    }

    private double getAngle(Point one){
        double angle = Math.atan2(one.getY() , one.getX()) * 180 / Math.PI;
        if(angle < 0)
            return angle + 360;
        else
            return angle;
    }

    private Point[] squareSort(Point[] vertices){
        //First organize the points in a counterclockwise fashion (check if the next point has less of an x and equal y, then if next has greater y more less x.
        //Then check for proper qualities such as sides are the same size.
        Point[] verticeCopy;
        verticeCopy = vertices;

        for(int i = 0; i<vertices.length; i++){
            for(int j = 0; j< vertices.length - 1; j++) {
                if (getAngle(vertices[j]) > getAngle(vertices[j+1])) {
                    Point holder = vertices[j];
                    vertices[j] = vertices[j + 1];
                    vertices[j + 1] = holder;
                }
            }
        }
        if(isSquare(vertices)){
            return vertices;
        } else {
            return verticeCopy;
        }
    }



}
