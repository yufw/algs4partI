import java.util.Arrays;

/**
 * Created by yufw on 2015/5/6.
 */
public class Brute {
    public static void main(String[] args) {
        String filename = args[0];
        In input = new In(filename);
        int total = input.readInt();
        Point[] points = new Point[total];
        for (int i = 0; i < total; i++) {
            int x = input.readInt();
            int y = input.readInt();
            Point p = new Point(x, y);
            points[i] = p;
        }

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points)
            p.draw();

        Arrays.sort(points);

        for (int i = 0; i < total; i++) {
            Point p = points[i];
            for (int j = i + 1; j < total; j++) {
                Point q = points[j];
                for (int k = j + 1; k < total; k++) {
                    Point r = points[k];
                    for (int h = k + 1; h < total; h++) {
                        Point s = points[h];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            p.drawTo(s);
                            System.out.println(p + " -> " + q + " -> " + r + " -> " + s);
                        }
                    }
                }
            }
        }
    }
}

