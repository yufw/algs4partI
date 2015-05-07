import java.util.Arrays;

/**
 * Created by yufw on 2015/5/6.
 */
public class Fast {
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

        if (total < 4)
            return;
        Arrays.sort(points);

        // make a copy
        Point[] points2 = new Point[total];
        for (int i = 0; i < total; i++)
            points2[i] = points[i];

        for (int i = 0; i < total; i++) {
            Point p = points2[i];
            Arrays.sort(points, p.SLOPE_ORDER);

            int start = 1;
            int end = 1;
            double prevSlope = p.slopeTo(points[1]);
            for (int j = 1; j < total; j++) {
                double slope = p.slopeTo(points[j]);
                if (slope == prevSlope) {
                    continue;
                } else {
                    end = j;
                    if (end - start >= 3 && p.compareTo(points[start]) < 0) {
                        p.drawTo(points[end-1]);
                        System.out.print(p);
                        for (int k = start; k < end; k++) {
                            System.out.print(" -> " + points[k]);
                        }
                        System.out.println();
                    }
                    prevSlope = p.slopeTo(points[j]);
                    start = j;

                }
            }
            if (total - start >= 3 && p.compareTo(points[start]) < 0) {
                p.drawTo(points[total-1]);
                System.out.print(p);
                for (int k = start; k < total; k++) {
                    System.out.print(" -> " + points[k]);
                }
                System.out.println();
            }
            Arrays.sort(points);
        }
    }
}
