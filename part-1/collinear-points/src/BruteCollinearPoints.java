import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints
{
    private List<Line> lines = new ArrayList<>();

    public BruteCollinearPoints(final Point[] points)
    {
        notNull(points);
        validatePoints(points);
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i + 1; j < points.length; j++)
            {
                for (int k = j + 1; k < points.length; k++)
                {
                    for (int index = k + 1; index < points.length; index++)
                    {
                        if (arePointsCollinear(points[i], points[j], points[k], points[index])
                            && isLineNew(points[i].slopeTo(points[index]), points[i]))
                        {
                            final Point[] tuple = {points[i], points[j], points[k], points[index]};
                            Arrays.sort(tuple);
                            lines.add(new Line(new LineSegment(tuple[0], tuple[3]), tuple[0], tuple[0].slopeTo(tuple[3])));
                        }
                    }
                }
            }
        }
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

    private boolean arePointsCollinear(final Point p, final Point q, final Point r, final Point s)
    {
        final double slope = p.slopeTo(q);
        return Double.compare(slope, p.slopeTo(r)) == 0 && Double.compare(slope, p.slopeTo(s)) == 0;
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

    private void validatePoints(final Point[] points)
    {
        for (int i = 0; i < points.length; i++)
        {
            notNull(points[i]);

            for (int j = 0; j < i; j++)
            {
                notEqual(points[j], points[i]);
            }
        }
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
