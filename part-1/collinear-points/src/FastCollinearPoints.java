import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints
{
    private List<Line> lines = new ArrayList<>();

    public FastCollinearPoints(final Point[] points)
    {
        notNull(points);
        final Point[] pointsArray = validatePoints(points);

        for (int i = 0; i < pointsArray.length; i++)
        {
            Arrays.sort(pointsArray, i + 1, pointsArray.length, pointsArray[i].slopeOrder());

            findCollinearPointsFor(pointsArray[i], pointsArray, i + 1);
        }
    }

    private void findCollinearPointsFor(final Point point, final Point[] points, final int index)
    {
        for (int i = index; i < points.length - 2; i++)
        {
            final Point[] tuple = getTuple(point, points, i);
            if (arePointsCollinear(tuple) && isLineNew(point.slopeTo(tuple[1]), point))
            {
                Arrays.sort(tuple);
                final Point[] minMax = checkForExtraPoints(tuple, point, points, i + 1);
                lines.add(new Line(new LineSegment(minMax[0], minMax[1]), minMax[0], minMax[0].slopeTo(minMax[1])));
            }
        }
    }

    private Point[] checkForExtraPoints(final Point[] tuple, final Point point, final Point[] points, final int index)
    {
        Point min = tuple[0], max = tuple[3];
        for (int i = index; i < points.length - 2 && arePointsCollinear(getTuple(point, points, i)); i++)
        {
            if (min.compareTo(points[i + 2]) > 0)
            {
                min = points[i + 2];
            }
            if (max.compareTo(points[i + 2]) < 0)
            {
                max = points[i + 2];
            }
        }
        return new Point[]{min, max};
    }

    private Point[] getTuple(final Point point, final Point[] points, final int i)
    {
        return new Point[]{point, points[i], points[i + 1], points[i + 2]};
    }

    public int numberOfSegments()
    {
        return lines.size();
    }

    public LineSegment[] segments()
    {
        return lines.stream()
            .map(line -> line.lineSegment)
            .toArray(LineSegment[]::new);
    }

    private boolean arePointsCollinear(final Point[] points)
    {
        final double slope = points[0].slopeTo(points[1]);
        return Double.compare(slope, points[0].slopeTo(points[2])) == 0
            && Double.compare(slope, points[0].slopeTo(points[3])) == 0;
    }

    private boolean isLineNew(final double slope, final Point p)
    {
        return lines.stream()
            .noneMatch(line -> areLinesEquivalent(slope, p, line));
    }

    private boolean areLinesEquivalent(final double slope, final Point p, final Line line)
    {
        return Double.compare(slope, line.slope) == 0
            && (p.compareTo(line.origin) == 0 || Double.compare(slope, line.origin.slopeTo(p)) == 0);
    }

    private Point[] validatePoints(final Point[] points)
    {
        final Point[] pointsCopy = new Point[points.length];

        for (int i = 0; i < points.length; i++)
        {
            notNull(points[i]);
            pointsCopy[i] = points[i];

            for (int j = 0; j < i; j++)
            {
                notEqual(pointsCopy[j], pointsCopy[i]);
            }
        }

        return pointsCopy;
    }

    private void notNull(final Object object)
    {
        if (object == null)
        {
            throw new IllegalArgumentException();
        }
    }

    private void notEqual(final Point point1, final Point point2)
    {
        if (point1.compareTo(point2) == 0)
        {
            throw new IllegalArgumentException();
        }
    }

    private class Line
    {
        private final LineSegment lineSegment;
        private final Point origin;
        private final double slope;

        Line(final LineSegment lineSegment, final Point origin, final double slope)
        {
            this.lineSegment = lineSegment;
            this.origin = origin;
            this.slope = slope;
        }
    }
}
