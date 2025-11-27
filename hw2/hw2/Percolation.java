package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF gridUnion;
    private final boolean[][] gridOpenStats;
    private final int[][] gridUnionIndex;
    private int openNum;
    private boolean isPercolated;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        gridOpenStats = new boolean[N][N];
        gridUnionIndex = new int[N][N];
        gridUnion = new WeightedQuickUnionUF(N * N + 2);
        openNum = 0;
        isPercolated = false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gridOpenStats[i][j] = false;
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > gridOpenStats.length - 1 || col < 0 || col > gridOpenStats.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (isOpen(row, col)) {
            return;
        }

        gridOpenStats[row][col] = true;
        gridUnionIndex[row][col] = gridOpenStats.length * row + col + 1;

        if (row == 0) {
            gridUnion.union(0, gridUnionIndex[row][col]);
        } else if (isOpen(row - 1, col)) {
            int preRow = row - 1;
            gridUnion.union(gridUnionIndex[preRow][col], gridUnionIndex[row][col]);
        }

        if (row == gridOpenStats.length - 1) {
            gridUnion.union(gridUnionIndex[row][col], gridOpenStats.length * gridOpenStats.length + 1);
        } else if (isOpen(row + 1, col)) {
            int nextRow = row + 1;
            gridUnion.union(gridUnionIndex[nextRow][col], gridUnionIndex[row][col]);
        }

        if (col > 0 && isOpen(row, col - 1)) {
            int preCol = col - 1;
            gridUnion.union(gridUnionIndex[row][preCol], gridUnionIndex[row][col]);
        }
        if (col < gridOpenStats.length - 1 && isOpen(row, col + 1)) {
            int nextCol = col + 1;
            gridUnion.union(gridUnionIndex[row][nextCol], gridUnionIndex[row][col]);
        }

        openNum += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > gridOpenStats.length - 1 || col < 0 || col > gridOpenStats.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        return gridOpenStats[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > gridOpenStats.length - 1 || col < 0 || col > gridOpenStats.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            return false;
        }

        return gridUnion.connected(gridUnionIndex[row][col], 0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    // naive approach.O(N) Haven't found a better way yet
//    public boolean percolates2() {
//        int lastRow = gridOpenStats.length - 1;
//        for (int i = 0; i < gridOpenStats.length; i++) {
//            if (!isOpen(lastRow, i)) {
//                continue;
//            }
//
//            if (gridUnion.connected(gridUnionIndex[lastRow][i], 0)) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    // another naive approach that caused "backwash". but time complexity is much better O(1)
    public boolean percolates() {
        return gridUnion.connected(gridOpenStats.length * gridOpenStats.length + 1, 0);
    }
}
