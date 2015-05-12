import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by yufw on 2015/5/11.
 */
public class Solver {
    private class ManhattanComparator implements Comparator<Node> {
        public int compare(Node x, Node y) {
            int px = (x.board.manhattan()+x.mvs);
            int py = (y.board.manhattan()+y.mvs);
            return Integer.compare(px, py);
        }
    }

    private class Node {
        public Board board;
        public int mvs;
        public Node prev;
    }
    private MinPQ<Node> minPQ = new MinPQ<Node>(new ManhattanComparator());
    private MinPQ<Node> minPQ2 = new MinPQ<Node>(new ManhattanComparator());
    private ArrayList<Board> solutions = new ArrayList<Board>();
    private ArrayList<Board> solutions2 = new ArrayList<Board>();
    private boolean solvable;
    private int steps;
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();
        Node node = new Node();
        node.board = initial;
        node.mvs = 0;
        node.prev = null;
        minPQ.insert(node);

        Node node2 = new Node();
        node2.board = initial.twin();
        node2.mvs = 0;
        node2.prev = null;
        minPQ2.insert(node2);

        Node x; // initial board
        Node y; // twin board
        do {
            x = minPQ.delMin();
            solutions.add(x.board);
            for (Board b : x.board.neighbors()) {
                if (x.prev != null && !(b.equals(x.prev.board)) || x.prev == null) {
                    Node n = new Node();
                    n.board = b;
                    n.mvs = x.mvs + 1;
                    n.prev = x;
                    minPQ.insert(n);
                }
            }
            y = minPQ2.delMin();
            solutions2.add(y.board);
            for (Board b : y.board.neighbors()) {
                if (y.prev != null && !(b.equals(y.prev.board)) || y.prev == null) {
                    Node n = new Node();
                    n.board = b;
                    n.mvs = y.mvs + 1;
                    n.prev = y;
                    minPQ2.insert(n);
                }
            }

        } while (!x.board.isGoal() && !y.board.isGoal());

        if (y.board.isGoal()) {
            solvable = false;
            steps = -1;
        } else {
            solvable = true;
            steps = x.mvs;
        }
    }
    public boolean isSolvable() {
        return solvable;
    }
    public int moves() {
        return steps;
    }
    public Iterable<Board> solution() {
        if (isSolvable())
            return solutions;
        else
            return null;
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
