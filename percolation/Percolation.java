/**
 * Created by yufw on 2015/5/2.
 */
public class Percolation {
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be greater than 0");
        w = new WeightedQuickUnionUF((N+1)*(N+1));
        w2 = new WeightedQuickUnionUF((N+1)*(N+1));
        is_open = new boolean[N+1][N+1];
        is_open2 = new boolean[N+1][N+1];

        for (int i = 1; i < is_open.length; i++)
            for (int j = 1; j < is_open[i].length; j++)
                is_open[i][j] = false;
        for (int i = 1; i < is_open2.length; i++)
            for (int j = 1; j < is_open2[i].length; j++)
                is_open2[i][j] = false;


        scale = N;

        for (int j = 1; j <= N; j++) {
            w.union(0, N + 1 + j);
            w.union(1, N*(N+1) + j);
        }
        for (int j = 1; j <= N; j++) {
            w2.union(0, N + 1 + j);
        }

    }

    public void open(int i, int j) {
        if (i < 1 || i > scale || j < 1 || j >scale)
            throw new IndexOutOfBoundsException("Index out of Bound");
        if (!isOpen(i, j)) {
            is_open[i][j] = true;
            is_open2[i][j] = true;
        }
        if (in_range(i, j-1) && isOpen(i, j-1)) {
            w.union(i*(scale+1)+j-1, i*(scale+1)+j);
            w2.union(i*(scale+1)+j-1, i*(scale+1)+j);
        }
        if (in_range(i, j+1) && isOpen(i, j+1)) {
            w.union(i*(scale+1)+j+1, i*(scale+1)+j);
            w2.union(i*(scale+1)+j+1, i*(scale+1)+j);
        }
        if (in_range(i-1, j) && isOpen(i-1, j)) {
            w.union((i-1)*(scale+1)+j, i*(scale+1)+j);
            w2.union((i-1)*(scale+1)+j, i*(scale+1)+j);
        }
        if (in_range(i+1, j) && isOpen(i+1, j)) {
            w.union((i+1)*(scale+1)+j, i*(scale+1)+j);
            w2.union((i+1)*(scale+1)+j, i*(scale+1)+j);
        }
    }
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > scale || j < 1 || j >scale)
            throw new IndexOutOfBoundsException("Index out of Bound");
        return is_open[i][j];
    }
    public boolean isFull(int i, int j) {
        if (i < 1 || i > scale || j < 1 || j >scale)
            throw new IndexOutOfBoundsException("Index out of Bound");
        return (isOpen(i, j) && w2.connected(i*(scale+1)+j, 0));
    }
    public boolean percolates() {
        if (scale == 1) {
            return isOpen(1, 1);
        } else {
            return w.connected(0, 1);
        }
    }

    private WeightedQuickUnionUF w;
    private WeightedQuickUnionUF w2;
    private boolean[][] is_open;
    private boolean[][] is_open2;
    private int scale;
    private boolean in_range(int i, int j) {
        return (i>=1 && i <= scale && j >= 1 && j <= scale);
    }
    public static void main(String[] args) {
        Percolation p = new Percolation(1);
        System.out.println(p.percolates());
        int counts = 0;
        int i, j;
        while (!p.percolates()) {
            i = StdRandom.uniform(1, p.scale+1);
            j = StdRandom.uniform(1, p.scale+1);
            if (p.isOpen(i, j))
                continue;
            else {
                p.open(i, j);
                counts++;
            }
        }
        System.out.println((double) counts/(p.scale*p.scale));
    }
}
