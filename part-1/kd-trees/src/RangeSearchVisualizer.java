import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class RangeSearchVisualizer
{
    public static void main(final String[] args)
    {
        String filename = args[0];
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

        double x0 = 0.0, y0 = 0.0;
        double x1 = 0.0, y1 = 0.0;
        boolean isDragging = false;

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();
        StdDraw.enableDoubleBuffering();

        while (true)
        {
            if (StdDraw.isMousePressed() && !isDragging)
            {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }
            else if (StdDraw.isMousePressed() && isDragging)
            {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }
            else if (!StdDraw.isMousePressed() && isDragging)
            {
                isDragging = false;
            }

            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            final RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);

            brute.range(rect).forEach(Point2D::draw);

            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.range(rect).forEach(Point2D::draw);

            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}
