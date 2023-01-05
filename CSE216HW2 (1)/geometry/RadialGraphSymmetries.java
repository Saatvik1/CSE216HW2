package geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

public class RadialGraphSymmetries implements Symmetries<RadialGraph> {
    @Override
    public boolean areSymmetric(RadialGraph s1, RadialGraph s2) {
        List<Point> s1Vertices = s1.getNeighbors();
        List<Point> s2Vertices = s2.getNeighbors();
        double[] displacement = new double[2];

        if(!(s1.center().getX() == s2.center().getX() && s1.center().getY() == s2.center().getY())){
            displacement[0] = s1.center().getX() - s2.center().getX();
            displacement[1] = s1.center().getY() - s2.center().getY();
        } else {
            displacement[0] = 0;
            displacement[1] = 0;
        }

        s2Vertices = shiftPoints(s2Vertices,displacement);

        for(Point vertices : s1Vertices){
            for(Point vs : s2Vertices){
                if(vs.getX() == vertices.getX() && vertices.getY() == vs.getY()){
                    return true;
                }
            }
        }

        return false;
    }

    private List<Point> shiftPoints(List<Point> vertices, double[] shift){
        List<Point> shiftedSquare = new ArrayList<>();
        for(Point vertice : vertices){
            shiftedSquare.add(new Point(vertice.getName(), vertice.getX() + shift[0], vertice.getY() + shift[1]));
        }

        return shiftedSquare;
    }

    @Override
    public List<RadialGraph> symmetriesOf(RadialGraph radialGraph) {

        ArrayList<RadialGraph> symmetries = new ArrayList<>();
        //Rotate 360 degrees.

        for(int i = 0; i<360; i++){
            RadialGraph rotatedTest = radialGraph.rotateBy(i);
            if(areSymmetric(radialGraph,rotatedTest)){
                symmetries.add(rotatedTest);
            }
        }

        //Swap points over center line (swap x values of every point based off position in the center), and rotate 360 degrees, if same then add
        for(int i = 0; i<360; i++){
            RadialGraph copy = radialGraph.rotateBy(i);
            double centerX = copy.center().getX();

            List<Point> flippedGraph = new ArrayList<>();

            for(Point point : copy.getNeighbors()){
                //If x val of point is left of center, adds 2 times the distance so it gets swapped to the other side.
                if(point.getX() < centerX){
                    flippedGraph.add(new Point(point.getName(), ((centerX - point.getX())  * 2) + point.getX(), point.getY()));
                    //If x val of point is right of center, subtract 2 times the distance so it gets swapped to the other side.
                } else if(point.getX() > centerX) {
                    //If over or under the center, keep the point where it is.
                    flippedGraph.add(new Point(point.getName(), ( point.getX() - (point.getX() - centerX)  * 2), point.getY()));
                } else {
                    flippedGraph.add(new Point(point.getName(), point.getX(), point.getY()));
                }
            }

            if(areSymmetric(radialGraph, new RadialGraph(copy.center(),flippedGraph))){
                symmetries.add(new RadialGraph(copy.center(),flippedGraph));
            }
        }

        return symmetries;
    }
}
