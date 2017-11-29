import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Sven Westerlaken on 12-10-2017.
 */
public class Percolation {

    private int size;
    private int rowSize;
    private int[] opened;
    private int count;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException();

        rowSize = n;
        size = n*n;
        count = 0;
        uf = new WeightedQuickUnionUF(n*n + 2);
        opened = new int[n*n];
    }

    public void open(int row, int col) {
        if(row <= 0 || row > rowSize || col <= 0 || col > rowSize) throw new IllegalArgumentException();

        int i = index(row, col);
        if(opened[i-1] == 0) {
            opened[i - 1] = 1;

            if(i % rowSize != 1) {
                if(isOpen(row, col-1)) {
                    uf.union(i, i-1);
                }
            }

            if(i % rowSize != 0) {
                if(isOpen(row, col+1)) {
                    uf.union(i, i+1);
                }
            }

            if(i - rowSize > 0) {
                if(isOpen(row-1, col)) {
                    uf.union(i, i-rowSize);
                }
            } else {
                uf.union(i,0);
            }

            if(i + rowSize <= size) {
                if(isOpen(row+1, col)) {
                    uf.union(i, i+rowSize);
                }
            } else {
                uf.union(i, size+1);
            }

            count++;
        }
    }

    public boolean isOpen(int row, int col) {
        if(row <= 0 || row > rowSize || col <= 0 || col > rowSize) throw new IllegalArgumentException();
        return opened[index(row, col) - 1] == 1;
    }

    public boolean isFull(int row, int col) {
        if(row <= 0 || row > rowSize || col <= 0 || col > rowSize) throw new IllegalArgumentException();
        return uf.connected(0, index(row, col));
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return uf.connected(0, size + 1);
    }

    private int index(int row, int col) {
        return rowSize * (row - 1) + col;
    }


}
