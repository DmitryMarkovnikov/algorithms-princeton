import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int dimensionSize;
    private final int gridSize;
    private final WeightedQuickUnionUF uf;
    private final int [] open;
    private int openSiteNumber;

    public Percolation(int number) {
        if (number <= 0) {
            throw new java.lang.IllegalArgumentException("number of grid should be positive");
        }
        this.openSiteNumber = 0;
        this.dimensionSize = number;
        this.gridSize = number * number;
        this.open = new int [this.gridSize];
        this.uf = new WeightedQuickUnionUF(this.gridSize + 2); // +2 here because +1 top site and +1 bottom site
    }

    private int xyTo1D(int row, int col) {
        return this.dimensionSize * (row - 1) + col - 1;
    }

    public boolean percolates() {
        return uf.connected(this.gridSize, this.gridSize+1);
    }

    private void validateSite(int row, int col) {
        if (row <= 0 || col > dimensionSize) {
            throw new java.lang.IllegalArgumentException("row index i out of bounds");
        }
        if (row <= 0 || col > dimensionSize) {
            throw new java.lang.IllegalArgumentException("row index i out of bounds");
        }
    }

    public void open(int row, int col) {
        this.validateSite(row, col);
        if (isOpen(row, col)) return;
        int position = this.xyTo1D(row, col);
        this.open[position] = 1;
        this.openSiteNumber++;
        this.connectNeighbours(row, col);
    }

    public boolean isOpen(int row, int col) {
        boolean opened = false;
        this.validateSite(row, col);
        int position = this.xyTo1D(row, col);

        if (this.open[position] == 1) {
            opened = true;
        }
        return opened;
    }

    public boolean isFull(int row, int col) {
        this.validateSite(row, col);
        return uf.connected(this.xyTo1D(row, col), this.gridSize);
    }

    private void connectNeighbours(int row, int col) {
        int currentPosition = xyTo1D(row, col);
        if (col < this.dimensionSize) this.union(row, col+1, currentPosition); // union to the right
        if (col > 1) this.union(row, col -1, currentPosition); // union to the left

        if (row < this.dimensionSize) this.union(row +1, col, currentPosition); // union  to the bottom
        else uf.union(currentPosition, this.gridSize + 1); // if row == N union to the virtual bottom

        if (row > 1) this.union(row -1, col, currentPosition);
        else uf.union(currentPosition, this.gridSize);

    }

    private void union(int row, int col, int index) {
        if (isOpen(row, col)) {
            uf.union(this.xyTo1D(row, col), index);
        }
    }

    public int numberOfOpenSites() {
        return this.openSiteNumber;
    }

}



