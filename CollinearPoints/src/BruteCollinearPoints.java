import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sven Westerlaken on 25-10-2017.
 */
public class BruteCollinearPoints {

    private final ArrayList<LineSegment> foundSegments;

    public BruteCollinearPoints(Point[] points) {
        checkArray(points);

        int n = points.length;
        foundSegments = new ArrayList<>();
        Arrays.sort(points);

        for (int p = 0; p < n - 3; p++) {
            for (int q = p + 1; q < n - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])) {
                        for (int s = r + 1; s < points.length; s++) {
                            if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                                    foundSegments.add(new LineSegment(points[p], points[s]));
                            }
                        }
                    }
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
}
