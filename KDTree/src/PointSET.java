import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 * Created by Sven Westerlaken on 5-11-2017.
 */
public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        checkForNull(p);
        points.add(p);
    }

    public boolean contains(Point2D p) {
        checkForNull(p);
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkForNull(rect);
        Queue<Point2D> queue = new Queue<>();
        for(Point2D p : points) {
            if(rect.contains(p)) {
                queue.enqueue(p);
            }
        }

        return queue;
    }

    public Point2D nearest(Point2D p) {
        checkForNull(p);
        Point2D champion = null;
        for(Point2D point : points) {
            if(champion == null || champion.distanceSquaredTo(p) > point.distanceSquaredTo(p)) {
                champion = point;
            }
        }

        return champion;

    }

    private void checkForNull(Object o) {
        if(o == null) throw new IllegalArgumentException();
    }
}
