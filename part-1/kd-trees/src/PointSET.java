import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class PointSET
{
    private final Set<Point2D> pointSet = new TreeSet<>();

    public boolean isEmpty()
    {
        return pointSet.isEmpty();
    }

    public int size()
    {
        return pointSet.size();
    }

    public void insert(final Point2D point)
    {
        validateArg(point);
        pointSet.add(point);
    }

    public boolean contains(final Point2D point)
    {
        validateArg(point);
        return pointSet.contains(point);
    }

    public void draw()
    {
        pointSet.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(final RectHV rect)
    {
        validateArg(rect);
        return pointSet.stream()
            .filter(rect::contains)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public Point2D nearest(final Point2D p)
    {
        validateArg(p);
        return pointSet.stream()
            .min(Comparator.comparingDouble(point -> point.distanceSquaredTo(p)))
            .orElse(null);
    }

    private void validateArg(final Object object)
    {
        if (object == null)
        {
            throw new IllegalArgumentException();
        }
    }
}
