import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sven Westerlaken on 29-10-2017.
 */
public class FastCollinearPoints {

    private final ArrayList<LineSegment> foundSegments;

    public FastCollinearPoints(Point[] points) {
        checkArray(points);

        int n = points.length;
        Point[] copy = points.clone();

        foundSegments = new ArrayList<>();



        for (int i = 0; i < n; i++) {
            Point current = copy[i];

            Arrays.sort(points);
            Arrays.sort(points, 0, n, current.slopeOrder());

            int lo = 1;
            int hi = 2;

            boolean isAscending = current.compareTo(points[lo]) < 0;

            while ( hi < n ) {
                if ( points[lo].slopeTo(current) == points[hi].slopeTo(current) ) {
                    if (points[hi].compareTo(current) < 0 && points[lo].compareTo(points[hi]) > 0) {
                        isAscending = false;
                    }
                } else {
                    if (isAscending && hi - lo >= 3) {
                        foundSegments.add(new LineSegment(current, points[hi-1]));
                    }

                    lo = hi;
                    isAscending = current.compareTo(points[lo]) < 0;

                }

                hi++;
            }

            if ( points[n-1].slopeTo(current) == points[lo].slopeTo(current) ) {
                if (isAscending && hi - lo >= 3) {
                    foundSegments.add(new LineSegment(current, points[n-1]));
                }
            }

        }

    }

    private void checkArray(Point[] points) {
        if ( points == null ) throw new IllegalArgumentException();
        if ( points[0] == null) throw new IllegalArgumentException();

        for ( int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {

                if(points[j] == null) {
                    throw new IllegalArgumentException();
                }

                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public int numberOfSegments() {
        return foundSegments.size();
    }

    public LineSegment[] segments() {
        return foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
