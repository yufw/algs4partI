import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

/**
 * Created by yufw on 2015/5/13.
 */
public class KdTree {
    private Node root;

    private class Node {
        private final Point2D p;
        private final int depth;
        private final RectHV rect;
        private int count;
        private Node left;
        private Node right;

        public Node(Point2D p, int depth, RectHV rect, int count) {
            this.p = p;
            this.depth = depth;
            this.rect = rect;
            this.count = count;
            this.left = null;
            this.right = null;
        }
    }

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.count;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        root = insert(root, p, 0, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Node x, Point2D p, int depth, double xmin, double ymin, double xmax, double ymax) {
        if (x == null) {
            return new Node(p, depth, new RectHV(xmin, ymin, xmax, ymax), 1);
        }

        if (x.p.equals(p)) {
            return x;
        }

        if (x.depth % 2 == 0) { // compare by x
            if (p.x() < x.p.x()) {
                x.left = insert(x.left, p, x.depth+1, x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                x.right = insert(x.right, p, x.depth+1, x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
            }
        } else { // compare by y
            if (p.y() < x.p.y()) {
                x.left = insert(x.left, p, x.depth + 1, x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            } else {
                x.right = insert(x.right, p, x.depth+1, x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
            }
        }

        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        if (x.depth % 2 == 0) {
            if (p.x() < x.p.x()) {
                return contains(x.left, p);
            } else {
                return contains(x.right, p);
            }
        } else {
            if (p.y() < x.p.y()) {
                return contains(x.left, p);
            } else {
                return contains(x.right, p);
            }
        }
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null)
            return;
        if (x.depth % 2 == 0) {
            StdDraw.setPenColor();
            StdDraw.filledCircle(x.p.x(), x.p.y(), 0.01);
            Point2D p1 = new Point2D(x.p.x(), x.rect.ymin());
            Point2D p2 = new Point2D(x.p.x(), x.rect.ymax());
            StdDraw.setPenColor(StdDraw.RED);
            p1.drawTo(p2);
        } else {
            StdDraw.setPenColor();
            StdDraw.filledCircle(x.p.x(), x.p.y(), 0.01);
            Point2D p1 = new Point2D(x.rect.xmin(), x.p.y());
            Point2D p2 = new Point2D(x.rect.xmax(), x.p.y());
            StdDraw.setPenColor(StdDraw.BLUE);
            p1.drawTo(p2);
        }
        draw(x.left);
        draw(x.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument is null");
        ArrayList<Point2D> res = new ArrayList<>();
        range(root, rect, res);
        return res;
    }

    private void range(Node x, RectHV rect, ArrayList<Point2D> tmp) {
        if (x == null) {
            return;
        }
        if (!rect.intersects(x.rect)) {
            return;
        }
        if (rect.contains(x.p)) {
            tmp.add(x.p);
        }
        range(x.left, rect, tmp);
        range(x.right, rect, tmp);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        return nearest(root, p, null);
    }

    private Point2D nearest(Node x, Point2D p, Point2D tmp) {
        if (x == null) {
            return tmp;
        }

        if (tmp != null && x.rect.distanceSquaredTo(p) >= tmp.distanceSquaredTo(p)) {
            return tmp;
        }

        if (tmp == null || x.p.distanceSquaredTo(p) < tmp.distanceSquaredTo(p)) {
            tmp = x.p;
        }
        if ((x.depth % 2 == 0 && p.x() < x.p.x()) || (x.depth % 2 != 0 && p.y() < x.p.y())) {
            tmp = nearest(x.left, p, tmp);
            tmp = nearest(x.right, p, tmp);
        } else {
            tmp = nearest(x.right, p, tmp);
            tmp = nearest(x.left, p, tmp);
        }

        return tmp;
    }

    public static void main(String[] args) {
        KdTree t = new KdTree();
        System.out.println(t.isEmpty());

        t.insert(new Point2D(0.7, 0.2));
        t.insert(new Point2D(0.5, 0.4));
        t.insert(new Point2D(0.2, 0.3));
        t.insert(new Point2D(0.4, 0.7));
        t.insert(new Point2D(0.9, 0.6));
        t.draw();

        System.out.println(t.size());
        System.out.println(t.contains(new Point2D(0.6, 0.5)));
        System.out.println(t.contains(new Point2D(0.5, 0.4)));
        System.out.println(t.nearest(new Point2D(0.8, 0.7)));

        System.out.println("------------------------");

        for (Point2D p : t.range(new RectHV(0.2, 0.2, 0.7, 0.7)))
            System.out.println(p);
    }
}
