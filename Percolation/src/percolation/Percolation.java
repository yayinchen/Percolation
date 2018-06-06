package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int n;
  private boolean[]id;	  
  private int countOpen = 0;
  private WeightedQuickUnionUF UF;
  private WeightedQuickUnionUF ufBot;
  int virtualTop;
  int virtualBot;
  public Percolation(int n) throws IllegalArgumentException {
    if (n<=0) {
		throw new IllegalArgumentException("N cannot be less than or equal to 0.");
	}
	this.n = n;
	id = new boolean[n*n+2];// create n-by-n grid, with all sites blocked
	UF = new WeightedQuickUnionUF(n * n + 2);
	ufBot = new WeightedQuickUnionUF(n * n +1);
	virtualTop = n * n;
	virtualBot = n * n + 1;
	id[virtualTop] = true;
	id[virtualBot] = true;   
  }
  public void open(int row, int col) {
    if(isOpen(row,col)) return;
	validate(row,col);
    int oneD  = xyTo1D(row, col);
	id[oneD] = true;
	countOpen ++;
    connectToOpen(row,col);}
  public boolean isOpen(int row, int col) {
    // is site (row, col) open?
    validate(row,col);
    return id[xyTo1D(row, col)] ;}
  public boolean isFull(int row, int col) {
  // is site (row, col) full?
    validate(row,col);
    if (isOpen(row,col) )return ufBot.connected(xyTo1D(row, col), virtualTop);
    return false;}
  public int numberOfOpenSites() {
    return countOpen;}
  public boolean percolates() {
    return UF.connected(virtualTop,virtualBot);
  }              // does the system percolate?
  private int xyTo1D(int row, int col) {
    //validate(row,col);
	return n*(row-1) + (col-1);}
  private void validate(int i,int j) {
    if (i<1 || j<1 || i>n ||j>n ) throw new IndexOutOfBoundsException();
  }
  private void connectToOpen(int row,int col) {
    int oneD = xyTo1D(row, col);
    if(row == 1) {
    	  UF.union(oneD, virtualTop);
    	  ufBot.union(oneD, virtualTop);
    }
    if(row == n) UF.union(oneD, virtualBot);
	if(row+1 <= n && isOpen(row+1, col)) {
		UF.union(oneD, xyTo1D(row+1, col));
		ufBot.union(oneD, xyTo1D(row+1, col));
	}
	if(row-1 >  0 && isOpen(row-1, col)) {
		UF.union(oneD, xyTo1D(row-1, col));
		ufBot.union(oneD, xyTo1D(row-1, col));
	}
	if(col-1 >  0 && isOpen(row, col-1)) {
		UF.union(oneD, xyTo1D(row, col-1));
		ufBot.union(oneD, xyTo1D(row, col-1));
	}
	if(col+1 <= n && isOpen(row, col+1)) {
		UF.union(oneD, xyTo1D(row, col+1));
		ufBot.union(oneD, xyTo1D(row, col+1));
	}
	}
  public static void main(String[] args) {
    Percolation p = new Percolation(5);
	// test client (optional)
	}
}

