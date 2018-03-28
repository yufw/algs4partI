import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
 
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }

        int total = points.length;
        for (int i = 0; i < total; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point is null");
            }
        }

        // make a copy
        Point[] points2 = new Point[total];
        for (int i = 0; i < total; i++) {
            points2[i] = points[i];
        }

        Arrays.sort(points2);
        for (int i = 1; i < total; i++) {
            if (points2[i-1].compareTo(points2[i]) == 0) {
                throw new IllegalArgumentException("argument contains a repeated point");
            }
        }

        for (int i = 0; i < total; i++) {
            Point p = points2[i];
            for (int j = i + 1; j < total; j++) {
                Point q = points2[j];
                for (int k = j + 1; k < total; k++) {
                    Point r = points2[k];
                    for (int h = k + 1; h < total; h++) {
                        Point s = points2[h];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            segments.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }   

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[segments.size()];
        return segments.toArray(seg);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
