import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BruteCollinearPointsTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArg()
    {
        new BruteCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullEntry()
    {
        final Point[] points = IntStream.range(0, 100)
            .mapToObj(i -> i == 50 ? null : new Point(i, i))
            .toArray(Point[]::new);

        new BruteCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForRepeatedPoints()
    {
        final Point[] points = IntStream.range(0, 100)
            .mapToObj(i -> i == 50 ? new Point(0, 0) : new Point(i, i))
            .toArray(Point[]::new);

        new BruteCollinearPoints(points);
    }

    @Test
    public void collinearPoints()
    {
        final Point[] points =
            {
                p(1, 1), p(2, 2), p(3, 3), p(4, 4),
                p(-1, 1), p(-2, 2), p(-3, 3), p(-4, 4),
                p(0, 1), p(0, 2), p(0, 3), p(0, 4),
                p(1, 0), p(2, 0), p(3, 0), p(4, 0)
            };
        StdRandom.shuffle(points);

        final BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        assertThat(bruteCollinearPoints.numberOfSegments(), is(4));

        final List<String> lines = stream(bruteCollinearPoints.segments())
            .map(LineSegment::toString)
            .collect(Collectors.toList());

        assertThat(lines, hasItems(
            new LineSegment(p(1, 1), p(4, 4)).toString(),
            new LineSegment(p(-1, 1), p(-4, 4)).toString(),
            new LineSegment(p(0, 1), p(0, 4)).toString(),
            new LineSegment(p(1, 0), p(4, 0)).toString()));
    }

    private Point p(final int x, final int y)
    {
        return new Point(x, y);
    }
}