import java.util.ArrayList;

/**
 * Created by yufw on 2015/5/13.
 */
public class PointSET {
    private SET<Point2D> points;
    public PointSET() {
        points = new SET<Point2D>();
    }
    public boolean  isEmpty() {
        return points.isEmpty();
    }
    public int size() {
        return points.size();
    }
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        points.add(p);
    }
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return points.contains(p);
    }
    public void draw() {
        for (Point2D p : points)
            p.draw();
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        ArrayList<Point2D> result = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
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
        PointSET pointSET = new PointSET();
        System.out.println(pointSET.isEmpty());
        pointSET.insert(new Point2D(0.1, 0.1));
        pointSET.insert(new Point2D(0.2, 0.2));
        pointSET.insert(new Point2D(0.5, 0.8));
        pointSET.insert(new Point2D(0.9, 0.4));
        RectHV rect = new RectHV(0.3, 0.3, 0.9, 0.9);
        System.out.println(pointSET.size());
        System.out.println(pointSET.contains(new Point2D(0.5, 0.5)));
        pointSET.draw();
        Point2D p = new Point2D(0.5, 0.5);
        p.draw();
        for (Point2D x :pointSET.range(rect))
            System.out.println(x);
        System.out.println(pointSET.nearest(p));
    }
}
