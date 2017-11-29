import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by Sven Westerlaken on 5-11-2017.
 */
public class KdTree {

    private Node root;
    private int size;

    public KdTree() {
        size = 0;
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    public void draw() {
        draw(root, true);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, queue);
        return queue;
    }

    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return nearest(root, p, root.p, true);
    }

    private Node insert(Node n, Point2D p, double xLowerBound, double yLowerBound, double xUpperBound, double yUpperBound, boolean vertical) {

        if (n == null) {
            RectHV r = new RectHV(xLowerBound, yLowerBound, xUpperBound, yUpperBound);
            size++;
            return new Node(p, r);
        }

        if (n.p.equals(p)) return n;

        if(vertical) {
            if(p.x() < n.p.x()) {
                n.left = insert(n.left, p, xLowerBound, yLowerBound, n.p.x(), yUpperBound, false);
            } else {
                n.right = insert(n.right, p, n.p.x(), yLowerBound, xUpperBound, yUpperBound, false);
            }
        } else {
            if(p.y() < n.p.y()) {
                n.left = insert(n.left, p, xLowerBound, yLowerBound, xUpperBound, n.p.y(), true);
            } else {
                n.right = insert(n.right, p, xLowerBound, n.p.y(), xUpperBound, yUpperBound, true);
            }
        }

        return n;

    }

    private boolean contains(Node n, Point2D p, boolean vertical) {
        if(n == null) return false;
        if(n.p.equals(p)) return true;

        if(vertical) {
            if(p.x() < n.p.x()) {
                return contains(n.left, p, false);
            } else {
                return contains(n.right, p, false);
            }
        } else {
            if(p.y() < n.p.y()) {
                return contains(n.left, p, true);
            } else {
                return contains(n.right, p, true);
            }
        }

    }

    private void draw(Node n, boolean vertical) {
        if ( n == null ) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        StdDraw.setPenRadius();

        if(vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.r.ymin(), n.p.x(), n.r.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.r.xmin(), n.p.y(), n.r.xmax(), n.p.y());
        }

        draw(n.left, !vertical);
        draw(n.right, !vertical);
    }

    private void range(Node n, RectHV r, Queue<Point2D> q) {
        if ( n == null ) return;

        if(r.contains(n.p)) {
            q.enqueue(n.p);
        }

        if(r.intersects(n.r)) {
            range(n.left, r, q);
            range(n.right, r, q);
        }
    }

    private Point2D nearest(Node n, Point2D p, Point2D c, boolean vertical) {
        Point2D champion = c;

        if (n == null) return champion;
        if (n.p.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
            champion = n.p;
        }

        if (n.r.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
            if(vertical && p.x() < n.p.x() || !vertical && p.y() < n.p.y()) {
                champion = nearest(n.left, p, champion, !vertical);
                champion = nearest(n.right, p , champion, !vertical);
            } else {
                champion = nearest(n.right, p, champion, !vertical);
                champion = nearest(n.left, p, champion, !vertical);
            }
        }

        return champion;
    }

    private static class Node {
        private Point2D p;
        private RectHV r;
        private Node left; // or down
        private Node right; // or up

        public Node(Point2D p, RectHV r) {
            this.p = p;
            this.r = r;
        }
    }

}
