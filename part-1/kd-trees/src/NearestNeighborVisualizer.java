import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class NearestNeighborVisualizer
{
    public static void main(final String[] args)
    {
        final String filename = args[0];
        final In in = new In(filename);
        final PointSET brute = new PointSET();
        final KdTree kdtree = new KdTree();

        while (!in.isEmpty())
        {
            final double x = in.readDouble();
            final double y = in.readDouble();
            final Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        StdDraw.enableDoubleBuffering();

        while (true)
        {
            final double x = StdDraw.mouseX();
            final double y = StdDraw.mouseY();
            final Point2D query = new Point2D(x, y);

            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
