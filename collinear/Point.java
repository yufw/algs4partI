/**
 * Created by yufw on 2015/5/6.
 */
import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            if (s1 < s2)
                return -1;
            else if (s1 > s2)
                return +1;
            else
                return 0;
        }
    }
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (x == that.x && y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if (x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (that.y == y)
            return +0.0;
        else
            return ((double) (that.y - y)) / (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (y < that.y)
            return -1;
        if (y > that.y)
            return +1;
        if (x < that.x)
            return -1;
        if (x > that.x)
            return +1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p1 = new Point(1000, 1000);
        Point p2 = new Point(1500, 2000);
        Point p3 = new Point(1200, 2000);
        Point[] points = new Point[3];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        Arrays.sort(points, p3.SLOPE_ORDER);
        for (Point p : points)
            System.out.println(p);
    }
}