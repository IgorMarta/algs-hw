import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class PercolationStats
{
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean, stddev, confidenceLo, confidenceHi;

    public PercolationStats(final int n, final int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException("n and trials must be > 0");
        }

        final double[] results = runMonteCarloTrials(n, trials);
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLo = mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
    }

    public double mean()
    {
        return mean;
    }

    public double stddev()
    {
        return stddev;
    }

    public double confidenceLo()
    {
        return confidenceLo;
    }

    public double confidenceHi()
    {
        return confidenceHi;
    }

    private double[] runMonteCarloTrials(final int n, final int trials)
    {
        return Arrays.stream(new double[trials])
            .map(i -> (double) runComputation(n) / (n * n))
            .toArray();
    }

    private int runComputation(final int n)
    {
        final Percolation percolation = new Percolation(n);

        while (!percolation.percolates())
        {
            percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
        }

        return percolation.numberOfOpenSites();
    }

    private void printResults()
    {
        System.out.printf("%-23s = %.16f%n%-23s = %.16f%n%-23s = [%.16f, %.16f]%n",
            "mean", mean, "stddev", stddev, "95% confidence interval", confidenceLo, confidenceHi);
    }

    public static void main(final String[] args)
    {
        if (args.length == 2)
        {
            final int n = Integer.parseInt(args[0]);
            final int trials = Integer.parseInt(args[1]);

            final PercolationStats percolationStats = new PercolationStats(n, trials);
            percolationStats.printResults();
        }
        else
        {
            System.out.println("The program expects 2 integer arguments: java PercolationStats <n> <T>");
        }
    }
}
