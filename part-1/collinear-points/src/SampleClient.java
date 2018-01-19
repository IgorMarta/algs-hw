import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SampleClient
{
    public static void main(final String[] args)
    {
        final In in = new In(args[0]);
        final int n = in.readInt();

        final Point[] points = new Point[n];

        for (int i = 0; i < n; i++)
        {
            final int x = in.readInt();
            final int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (final Point p : points)
        {
            p.draw();
        }

        StdDraw.show();

        final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);

        for (final LineSegment segment : fastCollinearPoints.segments())
        {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
        System.out.println(fastCollinearPoints.numberOfSegments());
    }
}
