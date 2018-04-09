import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FastCollinearPointsTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArg()
    {
        new FastCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullEntry()
    {
        final Point[] points = IntStream.range(0, 100)
            .mapToObj(i -> i == 50 ? null : new Point(i, i))
            .toArray(Point[]::new);

        new FastCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForRepeatedPoints()
    {
        final Point[] points = IntStream.range(0, 100)
            .mapToObj(i -> i == 50 ? new Point(0, 0) : new Point(i, i))
            .toArray(Point[]::new);

        new FastCollinearPoints(points);
    }

    @Test
    public void collinearPoints()
    {
        final Point[] points =
            {
                p(1, 1), p(2, 2), p(3, 3), p(4, 4),
                p(-1, 1), p(-2, 2), p(-3, 3), p(-4, 4), p(-5, 5),
                p(0, 1), p(0, 2), p(0, 3), p(0, 4),
                p(1, 0), p(2, 0), p(3, 0), p(4, 0)
            };
        StdRandom.shuffle(points);

        final FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);

        assertThat(fastCollinearPoints.numberOfSegments(), is(4));

        final List<String> lines = stream(fastCollinearPoints.segments())
            .map(LineSegment::toString)
            .collect(Collectors.toList());

        assertThat(lines, containsInAnyOrder(
            new LineSegment(p(1, 1), p(4, 4)).toString(),
            new LineSegment(p(-1, 1), p(-5, 5)).toString(),
            new LineSegment(p(0, 1), p(0, 4)).toString(),
            new LineSegment(p(1, 0), p(4, 0)).toString()));
    }

    private Point p(final int x, final int y)
    {
        return new Point(x, y);
    }
}