import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer
{
    public static void main(final String[] args)
    {
        final RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        final String filename = args[0];
        final In in = new In(filename);
        final KdTree kdtree = new KdTree();

        while (!in.isEmpty())
        {
            final double x = in.readDouble();
            final double y = in.readDouble();
            final Point2D p = new Point2D(x, y);
            StdOut.printf("%8.6f %8.6f\n", x, y);
            kdtree.insert(p);
        }

        kdtree.draw();
        StdDraw.show();
    }
}
