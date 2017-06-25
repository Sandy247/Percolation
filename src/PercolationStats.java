import edu.princeton.cs.algs4.StdOut;
import java.util.Random;

/**
 * Created by sandy on 6/20/17.
 */
public class PercolationStats {
    private int t;
    private double[] trials;
    public PercolationStats(int n,int t)
    {
        if (t <= 0 || n <= 0)
            throw new IllegalArgumentException("Invalid t or n value!!!");
        this.t = t;
        trials = new double[t];
        Random rnd = new Random();
        Percolation perc;
        for (int i = 0; i < t; i++) {
            perc = new Percolation(n);
            while (!perc.percolates())
                perc.open((rnd.nextInt(n) + 1), (rnd.nextInt(n) + 1));
            trials[i] = (perc.numberOfOpenSites() / (double) (n * n));
        }
    }
    public double mean()
    {
        double sum = 0;
        for (int i = 0; i < t; i++)
            sum += trials[i];
        return sum/t;
    }
    public double stddev()
    {
        double dev = 0, x = mean();
        for (int i = 0; i < t; i++)
            dev += Math.pow((trials[i]-x), 2);
        return Math.sqrt(dev/(t-1));
    }
    public double confidenceLo()
    {
        return mean()-1.96*stddev()/Math.sqrt(t);
    }
    public double confidenceHi()
    {
        return mean()+1.96*stddev()/Math.sqrt(t);
    }
    public static void main(String[] args)
    {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean\t\t\t= "+stats.mean()+"\n"+"stddev\t\t\t= "+stats.stddev()+"\n"+"95% confidence interval = ["+stats.confidenceLo()+", "+stats.confidenceHi()+"]");
    }
}
