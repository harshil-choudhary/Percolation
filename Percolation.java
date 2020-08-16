import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Harshil Choudhary

public class Percolation {
    private boolean[][] percolationGrid;                // boolean grid
    private final int gridDimension;                          // dimension of grid
    private final WeightedQuickUnionUF gridTopBottom;         // UF including the top and bottom sites
    private final int top;                                    // top site ID
    private final int bottom;                                 // bottom site ID            
    private int noOfOpenSites;                          // Total Number of Open Sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        gridDimension = n;
        percolationGrid = new boolean[n][n];
        noOfOpenSites = 0;
        gridTopBottom = new WeightedQuickUnionUF(n*n + 2);
        top = n*n;
        bottom = n*n+1;
    }

    // is site on the grid?
    private boolean siteIsOnGrid(int row, int col) {
        boolean siteIsOnGrid = ((row >= 1) && (col >= 1) && (row <= gridDimension) && (col <= gridDimension));
        return siteIsOnGrid;
    }

    // check site if its valid
    private void checkSite(int row, int col) {   
        if (!siteIsOnGrid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkSite(row, col);
        int gridIndex = sitesTillCoordinates(row, col)-1;
        
        if (isOpen(row, col)) {               // if site is already open
            return;
        }

        noOfOpenSites++;
        percolationGrid[row-1][col-1] = true;

        if (row == 1) {                                 // top row
            gridTopBottom.union(top, gridIndex);
        }

        if (row == gridDimension) {                     // bottom row
            gridTopBottom.union(top, gridIndex); 
        }

        if (siteIsOnGrid(row, col - 1) && isOpen(row, col - 1)) {           // connect with left
            gridTopBottom.union(gridIndex, sitesTillCoordinates(row, col - 1) - 1);
        }

        if (siteIsOnGrid(row, col - 1) && isOpen(row, col + 1)) {           // connect with right
            gridTopBottom.union(gridIndex, sitesTillCoordinates(row, col + 1) - 1);
        }

        if (siteIsOnGrid(row - 1, col) && isOpen(row - 1, col)) {           // connect with up
            gridTopBottom.union(gridIndex, sitesTillCoordinates(row - 1, col) - 1);
        }

        if (siteIsOnGrid(row + 1, col) && isOpen(row + 1, col)) {           // connect with down
            gridTopBottom.union(gridIndex, sitesTillCoordinates(row + 1, col) - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkSite(row, col);
        boolean site = percolationGrid[row-1][col-1];
        return site;
    }

    private int sitesTillCoordinates(int row, int col) {
        return gridDimension * (row - 1) + col;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkSite(row, col);
        int i = gridTopBottom.find(top);
        int j = gridTopBottom.find(sitesTillCoordinates(row, col) - 1);
        return i == j;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int i = gridTopBottom.find(top);
        int j = gridTopBottom.find(bottom);
        return i == j;
    }

    // test client (optional)
    public static void main(String[] args) {
        int dimension = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(dimension);
        int count = args.length;
        for (int i = 1; count >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("%nPercolates%n");
            }
            count -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }
    }
}