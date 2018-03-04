/**
 * Created by yufw on 2015/5/3.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N and T must be greater than 0");
        e_T = T;
        double [] frac = new double[T];
        for (int times = 0; times < T; times++) {
            Percolation p = new Percolation(N);
            int i, j;
            while (!p.percolates()) {
                i = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
                if (p.isOpen(i, j))
                    continue;
                else {
                    p.open(i, j);
                }
            }
            double f = (double) p.numberOfOpenSites() / (N*N);
            frac[times] = f;
        }
        u = StdStats.mean(frac);
        sigma = StdStats.stddev(frac);
    }
    public double mean() {
        return u;
    }
    public double stddev() {
        return sigma;
    }
    public double confidenceLo() {
        return (u-1.96*sigma/Math.sqrt(e_T));
    }
    public double confidenceHi() {
        return (u+1.96*sigma/Math.sqrt(e_T));
    }

    private int e_T;
    private double u;
    private double sigma;

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(N, T);
        double mean = p.mean();
        double stddev = p.stddev();
        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + stddev);
        System.out.println("95% confidence interval = " + p.confidenceLo() + ", " + p.confidenceHi());
    }
}
