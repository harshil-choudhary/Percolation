import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


// Harshil Choudhary

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trialCount;
    private final double[] trialResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int gridDimension = n;
        trialCount = trials;
        trialResults = new double[trialCount];

        for (int trial = 0; trial < trialCount; trial++) {
            Percolation percolation = new Percolation(gridDimension);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, gridDimension + 1);
                int col = StdRandom.uniform(1, gridDimension + 1);
                percolation.open(row, col);
            }
            int openSites = percolation.numberOfOpenSites();
            double result = (double) openSites / (gridDimension * gridDimension);
            trialResults[trial] = result;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trialCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trialCount));
    }

   // test client (see below)
   public static void main(String[] args) {
        int gridDimension = 1;
        int trials = 1;
        if (args.length >= 2) {
            gridDimension = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(gridDimension, trials);

        String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
   }

}