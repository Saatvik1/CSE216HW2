package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SquareSymmetries implements Symmetries<Square>{

    @Override
    public boolean areSymmetric(Square s1, Square s2) {
        List<Point> s1Vertices = Arrays.asList(s1.getVertices());
        List<Point> s2Vertices = Arrays.asList(s2.getVertices());
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
    public List<Square> symmetriesOf(Square square) {
        ArrayList<Square> symmetries = new ArrayList<>();

        for(int i = 0; i<360; i++){
            Square rotatedTest = square.rotateBy(i);
            if(areSymmetric(square,rotatedTest)){
                symmetries.add(rotatedTest);
            }
        }



        try{
            if(areSymmetric(square, new Square(square.getVertices()[1],square.getVertices()[0],square.getVertices()[3],square.getVertices()[2])))
                symmetries.add(new Square(square.getVertices()[1],square.getVertices()[0],square.getVertices()[3],square.getVertices()[2]));
        } catch(IllegalArgumentException e){}

        try{
            if(areSymmetric(square, new Square(square.getVertices()[3],square.getVertices()[2],square.getVertices()[1],square.getVertices()[0])))
                symmetries.add(new Square(square.getVertices()[3],square.getVertices()[2],square.getVertices()[1],square.getVertices()[0]));
        } catch(IllegalArgumentException e){}

        try{
            if(areSymmetric(square, new Square(square.getVertices()[0],square.getVertices()[3],square.getVertices()[2],square.getVertices()[1])))
                symmetries.add(new Square(square.getVertices()[0],square.getVertices()[3],square.getVertices()[2],square.getVertices()[1]));
        } catch(IllegalArgumentException e){}

        try{
            if(areSymmetric(square, new Square(square.getVertices()[2],square.getVertices()[1],square.getVertices()[0],square.getVertices()[3])))
                symmetries.add(new Square(square.getVertices()[3],square.getVertices()[2],square.getVertices()[1],square.getVertices()[0]));
        } catch(IllegalArgumentException e){}

        return symmetries;
    }
}
