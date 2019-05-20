import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONSTANT = 1.96;
    private final int dimensionSize;
    private final int trials;
    private double [] results;


    public PercolationStats(int width, int trials) {
        if (width <= 0) {
            throw new java.lang.IllegalArgumentException("width should not be negative");
        }

        if (trials <= 0) {
            throw new java.lang.IllegalArgumentException("grid size should not be negative");
        }

        this.dimensionSize = width;
        this.trials = trials;
        this.results = new double[trials];

        for (int i=0; i<this.trials; i++) {
            this.results[i] = this.runSimulation();
        }
    }

    public double mean()  {
        return StdStats.mean(this.results);
    }

    public double stddev() {
        return StdStats.stddev(this.results);
    }

    public double confidenceLo()  {
        return mean() - CONSTANT*stddev()/Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        return mean() + CONSTANT*stddev()/Math.sqrt(this.trials);
    }


    private double runSimulation() {
        Percolation perc = new Percolation(this.dimensionSize);
        int randomRow, randomCol;
        int count = 0;
        while (!perc.percolates()) {
            do {
                randomRow = StdRandom.uniform(this.dimensionSize) + 1;
                randomCol = StdRandom.uniform(this.dimensionSize) + 1;
            } while (perc.isOpen(randomRow,randomCol));
            count++;
            perc.open(randomRow, randomCol);
        }
        return count/(Math.pow(this.dimensionSize,2));
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int tries = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(gridSize, tries);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
