import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

/**
 * Created by yufw on 2015/5/11.
 */
public class Board {
    private final int dim;
    private final int blocks[][];
    private int row = -1;

    public Board(int[][] blocks) {
        dim = blocks.length;
        this.blocks = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                this.blocks[i][j] = blocks[i][j];
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int result = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i*dim+j+1)
                    result++;
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i*dim+j+1) {
                    int i_correct = (blocks[i][j]-1)/dim;
                    int j_correct = (blocks[i][j]-1)%dim;
                    result += (Math.abs(i-i_correct)+Math.abs(j-j_correct));
                }
            }
        }
        return result;
    }

    public boolean isGoal() {
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (blocks[i][j] != 0 && blocks[i][j] != i*dim+j+1)
                    return false;
        if (blocks[dim-1][dim-1] == 0)
            return true;
        else
            return false;
    }

    public Board twin() {
        if (row == -1) {
            while (true) {
                row = StdRandom.uniform(0, dim);
                if (blocks[row][0] != 0 && blocks[row][1] != 0)
                    break;
            }
        }

        int[][] twin_blocks = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                twin_blocks[i][j] = blocks[i][j];

        int temp = twin_blocks[row][0];
        twin_blocks[row][0] = twin_blocks[row][1];
        twin_blocks[row][1] = temp;

        return new Board(twin_blocks);
    }

    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (!(y instanceof Board))
            return false;
        if (this.dim != ((Board) y).dim)
            return false;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (blocks[i][j] != ((Board) y).blocks[i][j])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        int iBlank = 0;
        int jBlank = 0;
        boolean found = false;
        for (int i = 0; i < dim; i++) {
            if (found)
                break;
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    found = true;
                    iBlank = i;
                    jBlank = j;
                    break;
                }
            }
        }

        ArrayList<Board> neigh = new ArrayList<>();

        if (jBlank-1 >= 0) {
            int[][] neighBlocks = new int[dim][dim];
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    neighBlocks[i][j] = blocks[i][j];
            neighBlocks[iBlank][jBlank] = neighBlocks[iBlank][jBlank-1];
            neighBlocks[iBlank][jBlank-1] = 0;
            neigh.add(new Board(neighBlocks));
        }
        if (jBlank+1 < dim) {
            int[][] neighBlocks = new int[dim][dim];
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    neighBlocks[i][j] = blocks[i][j];
            neighBlocks[iBlank][jBlank] = neighBlocks[iBlank][jBlank+1];
            neighBlocks[iBlank][jBlank+1] = 0;
            neigh.add(new Board(neighBlocks));
        }
        if (iBlank-1 >= 0) {
            int[][] neighBlocks = new int[dim][dim];
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    neighBlocks[i][j] = blocks[i][j];
            neighBlocks[iBlank][jBlank] = neighBlocks[iBlank-1][jBlank];
            neighBlocks[iBlank-1][jBlank] = 0;
            neigh.add(new Board(neighBlocks));
        }
        if (iBlank+1<dim) {
            int[][] neighBlocks = new int[dim][dim];
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    neighBlocks[i][j] = blocks[i][j];
            neighBlocks[iBlank][jBlank] = neighBlocks[iBlank+1][jBlank];
            neighBlocks[iBlank+1][jBlank] = 0;
            neigh.add(new Board(neighBlocks));
        }
        return neigh;
    }

    public String toString() {
        String result = "";
        result += (dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (j != dim - 1)
                    result += (blocks[i][j] + " ");
                else if (i != dim - 1)
                    result += (blocks[i][j] + "\n");
                else
                    result += blocks[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] blocks = new int[][] {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(blocks);
        System.out.println(b);
        System.out.println(b.dimension());
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b.isGoal());
        System.out.println(b.twin());
        System.out.println(b);
        System.out.println(b.equals(b.twin()));
        for (Board neigh : b.neighbors())
            System.out.println(neigh);
    }
}
