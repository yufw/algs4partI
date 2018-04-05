import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by yufw on 2015/5/13.
 */
public class PointSET {
    private final TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument is null");
        ArrayList<Point2D> result = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        double shortest = Double.POSITIVE_INFINITY;
        Point2D result = null;
        for (Point2D x : points) {
            double current = p.distanceSquaredTo(x);
            if (current < shortest) {
                shortest = current;
                result = x;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        PointSET t = new PointSET();
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
