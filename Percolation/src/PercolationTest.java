import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Sven Westerlaken on 13-10-2017.
 */
public class PercolationTest {

    private static final int DELAY = 100;

    public static void main(String[] args) {
        int n = 10;
        if (args.length == 1) n = Integer.parseInt(args[0]);        // n-by-n percolation system
        StdDraw.enableDoubleBuffering();

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        PercolationVisualizer.draw(perc, n);
        StdDraw.show();
        StdDraw.pause(DELAY);



        while (!perc.percolates()) {
            perc.open(StdRandom.uniform(n)+1, StdRandom.uniform(1,n+1));
            PercolationVisualizer.draw(perc, n);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }

}
