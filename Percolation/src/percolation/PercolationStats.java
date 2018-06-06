package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  int N;
  int T;
  Percolation P ;
  double [] threshold;
  
  public PercolationStats(int n, int trials) throws java.lang.IllegalArgumentException{
	  if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("N and T cannot be less than or equal to 0.");
		}// perform trials independent experiments on an n-by-n grid
	  N = n;
	  T = trials;
	  threshold = new double[T];
	  for(int i = 0; i < T ; i++ ) {
		  P = new Percolation(N);
		  while(!P.percolates()) {
			  int p = StdRandom.uniform(1, N+1);
			  int q = StdRandom.uniform(1, N+1);
			  if(!P.isOpen(p, q)) {
				  P.open(p, q);
			  }
		  }
		  double count = P.numberOfOpenSites();
		  threshold[i] = count/(N*N);
	  }
  }
  public double mean() {
	  // sample mean of percolation threshold
      return StdStats.mean(threshold);
  }
  public double stddev() {
	  // sample standard deviation of percolation threshold
	  return StdStats.stddev(threshold);
  }
  public double confidenceLo() {
	  // low  endpoint of 95% confidence interval
	  return (mean()-((1.96*stddev())/(Math.sqrt(T))));
  }
  public double confidenceHi() {
	// high endpoint of 95% confidence interval
	 return (mean()+((1.96*stddev())/Math.sqrt(T)));
  }
  public static void main(String[] args){
	 int N = Integer.parseInt(args[0]);
	 int T = Integer.parseInt(args[1]);
	 PercolationStats Ps = new PercolationStats(N, T);
	 double L = Ps.confidenceLo();
	 double H = Ps.confidenceHi();
	 System.out.printf("mean %25s%f\n","= ",Ps.mean());
	 System.out.printf("stddev %23s%f\n","= ",Ps.stddev());
	 System.out.printf("95%% confidence interval %5s [%10f,%10f]\n", "=",L,H);
	 // test client (described below)
  }
}