import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by yufw on 2015/5/11.
 */
public class Solver {
    private boolean solvable;
    private int steps;
    private ArrayList<Board> solutions = new ArrayList<>();

    private class Node {
        public Board board;
        public Node prev;
        public int mvs;
        public int priority;
    }

    private class ManhattanComparator implements Comparator<Node> {
        public int compare(Node x, Node y) {
            return Integer.compare(x.priority, y.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("argument is null");

        MinPQ<Node> minPQ = new MinPQ<>(new ManhattanComparator());
        MinPQ<Node> minPQ2 = new MinPQ<>(new ManhattanComparator());

        Node node = new Node();
        node.board = initial;
        node.prev = null;
        node.mvs = 0;
        node.priority = node.board.manhattan() + node.mvs;
        minPQ.insert(node);

        Node node2 = new Node();
        node2.board = initial.twin();
        node2.prev = null;
        node2.mvs = 0;
        node2.priority = node2.board.manhattan() + node2.mvs;
        minPQ2.insert(node2);

        Node x; // initial board
        Node y; // twin board
        do {
            x = minPQ.delMin();
            for (Board b : x.board.neighbors()) {
                if (x.prev != null && !b.equals(x.prev.board) || x.prev == null) {
                    Node n = new Node();
                    n.board = b;
                    n.prev = x;
                    n.mvs = x.mvs + 1;
                    n.priority = n.board.manhattan() + n.mvs;
                    minPQ.insert(n);
                }
            }
            y = minPQ2.delMin();
            for (Board b : y.board.neighbors()) {
                if (y.prev != null && !b.equals(y.prev.board) || y.prev == null) {
                    Node n = new Node();
                    n.board = b;
                    n.prev = y;
                    n.mvs = y.mvs + 1;
                    n.priority = n.board.manhattan() + n.mvs;
                    minPQ2.insert(n);
                }
            }

        } while (!x.board.isGoal() && !y.board.isGoal());

        if (y.board.isGoal()) {
            solvable = false;
            steps = -1;
            solutions = null;
        } else {
            solvable = true;
            steps = x.mvs;
            while (x != null) {
                solutions.add(x.board);
                x = x.prev;
            }
            Collections.reverse(solutions);
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return steps;
    }

    public Iterable<Board> solution() {
        return solutions;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
