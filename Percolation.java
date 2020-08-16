import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final WeightedQuickUnionUF gridTopBottom;
    private final WeightedQuickUnionUF gridTop;
    private final int gridDimension;
    private final int top;
    private final int bottom;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be > 0");
        gridDimension = n;
        int gridSquared = n * n;
        grid = new boolean[gridDimension][gridDimension];
        gridTopBottom = new WeightedQuickUnionUF(gridSquared + 2); // includes virtual top bottom
        gridTop = new WeightedQuickUnionUF(gridSquared + 1); // includes virtual top
        bottom = gridSquared + 1;
        top = gridSquared;
        openSites = 0;

    }

    // Test: open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkSite(row, col);

        int shiftRow = row - 1;
        int shiftCol = col - 1;
        int index = elementsTillCoordinates(row, col) - 1;

        // If already open, stop
        if (isOpen(row, col)) {
            return;
        }

        // Open Site

        grid[shiftRow][shiftCol] = true;
        openSites++;

        if (row == 1) {  // Top Row
            gridTopBottom.union(top, index);
            gridTop.union(top, index);
        }

        if (row == gridDimension) {  // Bottom Row
            gridTopBottom.union(bottom, index);
        }

        // Check and Open Left
        if (isSiteOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            gridTopBottom.union(index, elementsTillCoordinates(row, col - 1) - 1);
            gridTop.union(index, elementsTillCoordinates(row, col - 1) - 1);
        }

        // Check and Open Right
        if (isSiteOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            gridTopBottom.union(index, elementsTillCoordinates(row, col + 1) - 1);
            gridTop.union(index, elementsTillCoordinates(row, col + 1) - 1);
        }

        // Check and Open Up
        if (isSiteOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            gridTopBottom.union(index, elementsTillCoordinates(row - 1, col) - 1);
            gridTop.union(index, elementsTillCoordinates(row - 1, col) - 1);
        }

        // Check and Open Down
        if (isSiteOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            gridTopBottom.union(index, elementsTillCoordinates(row + 1, col) - 1);
            gridTop.union(index, elementsTillCoordinates(row + 1, col) - 1);
        }

        // debug
        // runTests();
    }

    // Test: is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkSite(row, col);
        return grid[row - 1][col - 1];

    }

    // Test: is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkSite(row, col);
        int i = gridTop.find(top);
        int j = gridTop.find(elementsTillCoordinates(row, col) - 1);
        return (i == j);
    }

    // Test: number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // Test: does the system percolate?
    public boolean percolates() {
        int i = gridTopBottom.find(top);
        int j = gridTopBottom.find(bottom);
        return (i == j);
    }

    // test client
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("%nThe System percolates %n");
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }

    }

    private int elementsTillCoordinates(int row, int col) {
        return gridDimension * (row - 1) + col;
    }

    private void checkSite(int row, int col) {
        if (!isSiteOnGrid(row, col)) {
            throw new IllegalArgumentException("Row or Col out of bounds");
        }
    }

    private boolean isSiteOnGrid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < gridDimension && shiftCol < gridDimension);
    }
}

