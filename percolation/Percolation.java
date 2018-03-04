/**
 * Created by yufw on 2015/5/2.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be greater than 0");
        w = new WeightedQuickUnionUF(N*N+2);
        w2 = new WeightedQuickUnionUF(N*N+1);
        is_open = new boolean[N][N];
        top = N*N;
        bottom = N*N+1;

        for (int i = 0; i < is_open.length; i++)
            for (int j = 0; j < is_open[i].length; j++)
                is_open[i][j] = false;

        scale = N;

        for (int j = 1; j <= N; j++) {
            w.union(top, indexOf(1, j));
            w.union(bottom, indexOf(N, j));
            w2.union(top, indexOf(1, j));
        }

    }

    public void open(int i, int j) {
        if (i < 1 || i > scale || j < 1 || j > scale)
            throw new IllegalArgumentException("Index out of Bound");
        if (!isOpen(i, j)) {
            openSites++;
            is_open[i-1][j-1] = true;
        }
        if (in_range(i, j-1) && isOpen(i, j-1)) {
            w.union(indexOf(i, j), indexOf(i, j-1));
            w2.union(indexOf(i, j), indexOf(i, j-1));
        }
        if (in_range(i, j+1) && isOpen(i, j+1)) {
            w.union(indexOf(i, j), indexOf(i, j+1));
            w2.union(indexOf(i, j), indexOf(i, j+1));
        }
        if (in_range(i-1, j) && isOpen(i-1, j)) {
            w.union(indexOf(i, j), indexOf(i-1, j));
            w2.union(indexOf(i, j), indexOf(i-1, j));
        }
        if (in_range(i+1, j) && isOpen(i+1, j)) {
            w.union(indexOf(i, j), indexOf(i+1, j));
            w2.union(indexOf(i, j), indexOf(i+1, j));
        }
    }
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > scale || j < 1 || j >scale)
            throw new IllegalArgumentException("Index out of Bound");
        return is_open[i-1][j-1];
    }
    public boolean isFull(int i, int j) {
        if (i < 1 || i > scale || j < 1 || j >scale)
            throw new IllegalArgumentException("Index out of Bound");
        return (isOpen(i, j) && w2.connected(indexOf(i, j), top));
    }
    public int numberOfOpenSites() {
        return openSites;
    }
    public boolean percolates() {
        if (scale == 1) {
            return isOpen(1, 1);
        } else {
            return w.connected(top, bottom);
        }
    }


    private int top;
    private int bottom;
    private WeightedQuickUnionUF w;
    private WeightedQuickUnionUF w2;
    private int openSites = 0;
    private boolean[][] is_open;
    private int scale;

    private boolean in_range(int i, int j) {
        return (i>=1 && i <= scale && j >= 1 && j <= scale);
    }

    private int indexOf(int i, int j) {
        return (i-1)*scale + (j-1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        System.out.println(p.percolates());
        p.open(1, 3);
        p.open(2, 3);
        System.out.println(p.percolates());
        p.open(3, 3);
        System.out.println(p.percolates());
        p.open(3, 1);
        System.out.println(p.isFull(3, 1));
    }
}
