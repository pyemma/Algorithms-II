import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    
    private class SlopeOrder implements Comparator<Point> {
        
        public int compare(Point v, Point w) {
            
            double sl1 = slopeTo(v);
            double sl2 = slopeTo(w);
            
            if(sl1 < sl2)
                return -1;
            else if(sl1 > sl2)
                return 1;
            else
                return 0;
        }
    }

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        
        double x0 = this.x;
        double y0 = this.y;
        
        double x1 = that.x;
        double y1 = that.y;
        
        double dx = x1 - x0;
        double dy = y1 - y0;
        
        if(dx == 0){
            if(dy == 0)
                return Double.NEGATIVE_INFINITY;
            else
                return Double.POSITIVE_INFINITY;
        }
        else{
            if(dy == 0)
                return 0;
            else
                return dy/dx;
        }
            
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        
        if(this.y != that.y)
            return this.y - that.y;
        else
            return this.x - that.x;
        
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        
        Point[] p = new Point[10];
        for(int i = 0; i < 10; i++){
            p[i] = new Point(1, i);
        }
        Arrays.sort(p, 0, 10, p[0].SLOPE_ORDER);
        for(int i = 0; i < 10; i++){
            StdOut.println(p[i].toString());
            StdOut.println(p[i].slopeTo(p[0]));
        }
        StdOut.println(p[1].slopeTo(p[0]) == p[2].slopeTo(p[0]));
    }
}