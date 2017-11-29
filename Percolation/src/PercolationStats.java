import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Sven Westerlaken on 13-10-2017.
 */
public class PercolationStats {
    private double[] results;
    private double T;

    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0) throw new IllegalArgumentException();

        Percolation perc;
        results = new double[trials];
        double gridSize = n*n;
        T = trials;

        for(int i=0; i < trials; i++) {
            perc = new Percolation(n);

            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(n)+1, StdRandom.uniform(1,n+1));
            }

            results[i] = perc.numberOfOpenSites() / gridSize;
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int n = 10;
        int T = 1000;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }

        PercolationStats stats = new PercolationStats(n, T);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }
}
