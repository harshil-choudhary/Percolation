import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//Harshil Choudhary

public class Percolation {
    private boolean[][] percolationGrid;                //boolean grid
    private int gridDimension;                          //dimension of grid
    private WeightedQuickUnionUF gridTopBottom;         //UF including the top and bottom sites
    private WeightedQuickUnionUF gridTop;               //UF including the top site
    private int top;                                    //top site ID
    private int bottom;                                 //bottom site ID            
    private int noOfOpenSites;                          //Total Number of Open Sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n<=0) throw new IllegalArgumentException();
        gridDimension = n;
        percolationGrid = new boolean[n][n];
        noOfOpenSites = 0;
        gridTop = new WeightedQuickUnionUF(n*n + 1);
        gridTopBottom = new WeightedQuickUnionUF(n*n + 2);
        top = n*n;
        bottom = n*n+1;
    }

    // is site on the grid?
    private boolean siteIsOnGrid(int row, int col) {
        boolean siteIsOnGrid = ((row >= 1) && (col >= 1) && (row <= gridDimension) && (col <= gridDimension);
        return siteIsOnGrid;
    }

    //check site if its valid
    private void checkSite(int row, int col) {   
        if (!siteIsOnGrid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }

    // opens the site (row, col) if it is not open already
    //public void open(int row, int col)

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checkSite(row, col);
        boolean site = percolationGrid[row-1][col-1];
        return site;
    }

    //is the site (row, col) full?
    public boolean isFull(int row, int col){
        checkSite(row, col);
        boolean site = percolationGrid[row-1][col-1];
    }

    //returns the number of open sites
    public int numberOfOpenSites(){
        return noOfOpenSites;
    }

    // does the system percolate?
    //public boolean percolates()

    // test client (optional)
    //public static void main(String[] args)
}