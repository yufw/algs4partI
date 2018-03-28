import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
 
    public FastCollinearPoints(Point[] points) {
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

        if (total < 4) {
            return;
        }

        for (int i = 0; i < total; i++) {
            Point p = points[i];

            Arrays.sort(points2);
            Arrays.sort(points2, p.slopeOrder());

            int start = 1;
            int end;
            double prevSlope = p.slopeTo(points2[1]);
            for (int j = 1; j < total; j++) {
                double slope = p.slopeTo(points2[j]);
                if (slope != prevSlope) {
                    end = j;
                    if (end - start >= 3 && p.compareTo(points2[start]) < 0) {
                        segments.add(new LineSegment(p, points2[end-1]));
                    }
                    prevSlope = p.slopeTo(points2[j]);
                    start = j;

                }
            }
            if (total - start >= 3 && p.compareTo(points2[start]) < 0) {
                segments.add(new LineSegment(p, points2[total-1]));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
