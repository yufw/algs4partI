/**
 * Created by yufw on 2015/5/3.
 */
public class PercolationStats {
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N and T must be greater than 0");
        e_T = T;
        frac = new double[T];
        for (int times = 0; times < T; times++) {
            Percolation p = new Percolation(N);
            int counts = 0;
            int i, j;
            while (!p.percolates()) {
                i = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
                if (p.isOpen(i, j))
                    continue;
                else {
                    p.open(i, j);
                    counts++;
                }
            }
            double f = (double) counts / (N*N);
            frac[times] = f;
        }
    }
    public double mean() {
        return StdStats.mean(frac);
    }
    public double stddev() {
        return StdStats.stddev(frac);
    }
    public double confidenceLo() {
        double u = mean();
        double sigma = stddev();
        return (u-1.96*sigma/Math.sqrt(e_T));
    }
    public double confidenceHi() {
        double u = mean();
        double sigma = stddev();
        return (u+1.96*sigma/Math.sqrt(e_T));
    }

    private double[] frac;
    private int e_T;

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
