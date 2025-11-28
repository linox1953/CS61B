package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF virtualGridUnion;
    private final WeightedQuickUnionUF actualGridUnion;
    private final int firstRowVirtualNode;
    private final int lastRowVirtualNode;

    private final boolean[][] gridOpenStats;
    private final int[][] gridUnionIndex;
    private int openNum;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        gridOpenStats = new boolean[N][N];
        gridUnionIndex = new int[N][N];
        virtualGridUnion = new WeightedQuickUnionUF(N * N + 2);
        actualGridUnion = new WeightedQuickUnionUF(N * N + 1);
        openNum = 0;

        firstRowVirtualNode = N * N;
        lastRowVirtualNode = N * N + 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gridOpenStats[i][j] = false;
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > gridOpenStats.length - 1 ||
            col < 0 || col > gridOpenStats.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (isOpen(row, col)) {
            return;
        }

        gridOpenStats[row][col] = true;
        gridUnionIndex[row][col] = gridOpenStats.length * row + col;

        int preRow = row - 1;
        int nextRow = row + 1;
        int preCol = col - 1;
        int nextCol = col + 1;

        if (row == 0) {
            virtualGridUnion.union(firstRowVirtualNode, gridUnionIndex[row][col]);
            actualGridUnion.union(firstRowVirtualNode, gridUnionIndex[row][col]);
        } else if (isOpen(preRow, col)) { // 等同于 row > 0 && isOpen(preRow, col)
            virtualGridUnion.union(gridUnionIndex[preRow][col], gridUnionIndex[row][col]);
            actualGridUnion.union(gridUnionIndex[preRow][col], gridUnionIndex[row][col]);
        }

        if (row == gridOpenStats.length - 1) {
            virtualGridUnion.union(gridUnionIndex[row][col], lastRowVirtualNode);
        } else if (isOpen(nextRow, col)) {
            virtualGridUnion.union(gridUnionIndex[nextRow][col], gridUnionIndex[row][col]);
            actualGridUnion.union(gridUnionIndex[nextRow][col], gridUnionIndex[row][col]);
        }

        if (col > 0 && isOpen(row, preCol)) {
            virtualGridUnion.union(gridUnionIndex[row][preCol], gridUnionIndex[row][col]);
            actualGridUnion.union(gridUnionIndex[row][preCol], gridUnionIndex[row][col]);
        }
        if (col < gridOpenStats.length - 1 && isOpen(row, nextCol)) {
            virtualGridUnion.union(gridUnionIndex[row][nextCol], gridUnionIndex[row][col]);
            actualGridUnion.union(gridUnionIndex[row][nextCol], gridUnionIndex[row][col]);
        }

        openNum += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > gridOpenStats.length - 1 ||
            col < 0 || col > gridOpenStats.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        return gridOpenStats[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > gridOpenStats.length - 1 ||
            col < 0 || col > gridOpenStats.length - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            return false;
        }

        return actualGridUnion.connected(gridUnionIndex[row][col], firstRowVirtualNode);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // 为了解决 "washback" 问题和时间复杂度的最终实现
    // 即再创建一个并查集, 只与第一行的虚拟节点连接
    // 在 isFull 方法中检查新建并查集和第一行虚拟节点连接情况即可
    public boolean percolates() {
        return virtualGridUnion.connected(lastRowVirtualNode, firstRowVirtualNode);
    }

/*
    // does the system percolate?
    // naive approach. O(N) Haven't found a better way yet
    public boolean percolates() {
        int lastRow = gridOpenStats.length - 1;
        for (int i = 0; i < gridOpenStats.length; i++) {
            if (!isOpen(lastRow, i)) {
                continue;
            }

            if (virtualGridUnion.connected(gridUnionIndex[lastRow][i], 0)) {
                return true;
            }
        }

        return false;
    }
*/

    public static void main(String[] args) {

    }
}
