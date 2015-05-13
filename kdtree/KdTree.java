import java.util.ArrayList;

/**
 * Created by yufw on 2015/5/13.
 */
public class KdTree {
    private KdBST kdBST;
    private class KdBST {
        private Node root;
        private class Node {
            private Point2D p;
            private RectHV rect;
            private int depth;
            private int N;
            private Node left, right;

            public Node(Point2D p, int depth) {
                this.p = p;
                this.rect = null;
                this.depth = depth;
                this.N = 0;
                this.left = null;
                this.right = null;
            }
        }
        public KdBST() {
            root = null;
        }
        public boolean isEmpty() {
            return root == null;
        }
        public int size() {
            if (root == null)
                return 0;
            else
                return root.N;
        }
        public void insertPoint(Point2D p) {
            if (contains(p))
                return;
            Node parent = null;
            Node current = root;
            if (root == null) {
                root = new Node(p, 0);
                root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                while (current != null) {
                    parent = current;
                    if (current.depth % 2 == 0) { // split by x
                        if (p.x() < current.p.x()) { // go left
                            current = current.left;
                        } else { // go right
                            current = current.right;
                        }
                    } else { // split by y
                        if (p.y() < current.p.y()) { // go down
                            current = current.left;
                        } else { // go up
                            current = current.right;
                        }
                    }
                }
                if (parent.depth%2 == 0) {
                    if (p.x() < parent.p.x()) {
                        Node n = new Node(p, parent.depth + 1);
                        n.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                parent.p.x(), parent.rect.ymax());
                        parent.left = n;
                    }
                    else {
                        Node n = new Node(p, parent.depth + 1);
                        n.rect = new RectHV(parent.p.x(), parent.rect.ymin(),
                                parent.rect.xmax(), parent.rect.ymax());
                        parent.right = n;
                    }
                } else {
                    if (p.y() < parent.p.y()) {
                        Node n = new Node(p, parent.depth + 1);
                        n.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                parent.rect.xmax(), parent.p.y());
                        parent.left = n;
                    }
                    else {
                        Node n = new Node(p, parent.depth + 1);
                        n.rect = new RectHV(parent.rect.xmin(), parent.p.y(),
                                parent.rect.xmax(), parent.rect.ymax());
                        parent.right = n;
                    }
                }
            }
            current = root;
            while (current != null) {
                current.N += 1;
                if (current.depth % 2 == 0) { // split by x
                    if (p.x() < current.p.x()) { // go left
                        current = current.left;
                    } else { // go right
                        current = current.right;
                    }
                } else { // split by y
                    if (p.y() < current.p.y()) { // go down
                        current = current.left;
                    } else { // go up
                        current = current.right;
                    }
                }
            }
        }
        public boolean contains(Point2D p) {
            Node current = root;
            while (current != null && !current.p.equals(p)) {
                if (current.depth % 2 == 0) { // split by x
                    if (p.x() < current.p.x()) { // go left
                        current = current.left;
                    } else { // go right
                        current = current.right;
                    }
                } else { // split by y
                    if (p.y() < current.p.y()) { // go down
                        current = current.left;
                    } else { // go up
                        current = current.right;
                    }
                }
            }
            if (current == null)
                return false;
            else
                return true;
        }
        public Point2D nearest(Point2D p) {
            if (isEmpty())
                return null;

            currentBest = null;
            shortest = Double.POSITIVE_INFINITY;
            check(root, p);
            return currentBest;
        }
        private Point2D currentBest = null;
        private double shortest = Double.POSITIVE_INFINITY;

        private void check(Node n, Point2D p) {
            if (n == null)
                return;

            if (shortest <= n.rect.distanceSquaredTo(p))
                return;

            if (n.rect.distanceSquaredTo(p) < shortest) {
                if (n.p.distanceSquaredTo(p) < shortest) {
                    shortest = n.p.distanceSquaredTo(p);
                    currentBest = n.p;
                }
            }

            if (n.depth % 2 == 0) {
                if (p.x() < n.p.x()) {
                    check(n.left, p);
                    check(n.right, p);
                } else {
                    check(n.right, p);
                    check(n.left, p);
                }
            } else {
                if (p.y() < n.p.y()) {
                    check(n.left, p);
                    check(n.right, p);
                } else {
                    check(n.right, p);
                    check(n.left, p);
                }
            }
        }

        private ArrayList<Point2D> result;
        public Iterable<Point2D> range(RectHV rect) {
            result = new ArrayList<Point2D>();
            check2(root, rect);
            return result;
        }
        private void check2(Node n, RectHV rect) {
            if (n == null)
                return;
            if (!rect.intersects(n.rect))
                return;
            if (rect.contains(n.p)) {
                result.add(n.p);
            }

            check2(n.left, rect);
            check2(n.right, rect);
        }
        public void draw() {
            draw(root);
        }
        private void draw(Node n) {
            if (n == null)
                return;
            StdDraw.setPenColor();
            if (n.depth % 2 == 0) {
                StdDraw.filledCircle(n.p.x(), n.p.y(), 0.01);
                Point2D p1 = new Point2D(n.p.x(), n.rect.ymin());
                Point2D p2 = new Point2D(n.p.x(), n.rect.ymax());
                StdDraw.setPenColor(StdDraw.RED);
                p1.drawTo(p2);
            } else {
                StdDraw.filledCircle(n.p.x(), n.p.y(), 0.01);
                Point2D p1 = new Point2D(n.rect.xmin(), n.p.y());
                Point2D p2 = new Point2D(n.rect.xmax(), n.p.y());
                StdDraw.setPenColor(StdDraw.BLUE);
                p1.drawTo(p2);
            }
            draw(n.left);
            draw(n.right);
        }
    }

    public KdTree() {
        kdBST = new KdBST();
    }
    public boolean isEmpty() {
        return kdBST.isEmpty();
    }
    public int size() {
        return kdBST.size();
    }
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        kdBST.insertPoint(p);
    }
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return kdBST.contains(p);
    }
    public void draw() {
        kdBST.draw();
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        return kdBST.range(rect);
    }
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return kdBST.nearest(p);
    }
    public static void main(String[] args) {
        KdTree t = new KdTree();
        System.out.println(t.isEmpty());
        t.insert(new Point2D(0.7, 0.2));
        t.insert(new Point2D(0.5, 0.4));
        t.insert(new Point2D(0.2, 0.3));
        t.insert(new Point2D(0.4, 0.7));
        t.insert(new Point2D(0.9, 0.6));
        System.out.println(t.size());
        System.out.println(t.contains(new Point2D(0.6, 0.5)));
        System.out.println(t.contains(new Point2D(0.5, 0.4)));
        System.out.println(t.nearest(new Point2D(0.8, 0.7)));
        System.out.println("------------------------");

        for (Point2D p : t.range(new RectHV(0.2, 0.2, 0.7, 0.7)))
            System.out.println(p);

        t.draw();
    }
}
