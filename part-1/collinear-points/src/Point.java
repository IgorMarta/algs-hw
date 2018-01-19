import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point>
{
    private final int x;
    private final int y;

    public Point(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw()
    {
        StdDraw.point(x, y);
    }

    public void drawTo(final Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(final Point that)
    {
        final int deltaX = that.x - this.x, deltaY = that.y - this.y;

        if (deltaY == 0)
        {
            if (deltaX == deltaY)
            {
                return Double.NEGATIVE_INFINITY;
            }
            return 0.0;
        }

        if (deltaX == 0)
        {
            return Double.POSITIVE_INFINITY;
        }

        return (double) deltaY / deltaX;
    }

    public Comparator<Point> slopeOrder()
    {
        return Comparator.comparingDouble(this::slopeTo);
    }

    @Override
    public int compareTo(final Point that)
    {
        return this.y == that.y ? this.x - that.x : this.y - that.y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
