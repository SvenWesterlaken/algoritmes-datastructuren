import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if ( this.y == that.y && this.x == that.x ) return Double.NEGATIVE_INFINITY;
        if ( this.x == that.x ) return Double.POSITIVE_INFINITY;
        if ( this.y == that.y ) return 0.0;

        return (that.y - this.y) / (double) (that.x - this.x);
    }

    public int compareTo(Point that) {
        if ( this.y < that.y) return -1;
        if ( this.y > that.y) return 1;
        if ( this.x < that.x) return -1;
        if ( this.x > that.x) return 1;
        return 0;
    }

    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }


    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    private class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point point1, Point point2) {
            if ( slopeTo(point1) < slopeTo(point2) ) return -1;
            if ( slopeTo(point1) > slopeTo(point2) ) return 1;
            return 0;
        }
    }
}
