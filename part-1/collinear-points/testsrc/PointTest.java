import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class PointTest
{
    private Point thisPoint;
    private Point thatPoint;

    @Test
    public void slopeTo() throws Exception
    {
        initPoints(0, 2, 2, 2);
        assertThat(thisPoint.slopeTo(thatPoint), is(0.0));

        initPoints(1, 1, 1, 4);
        assertThat(thisPoint.slopeTo(thatPoint), is(Double.POSITIVE_INFINITY));
        assertThat(thisPoint.slopeTo(thisPoint), is(Double.NEGATIVE_INFINITY));

        initPoints(1, 3, 3, 4);
        assertThat(thisPoint.slopeTo(thatPoint), is(0.5));
    }

    @Test
    public void slopeOrder() throws Exception
    {
        thisPoint = new Point(0, 0);
        final Point p1 = new Point(1, 2);
        final Point p2 = new Point(1, 4);
        final Comparator<Point> comparator = thisPoint.slopeOrder();

        assertThat(comparator.compare(p1, p2), is(-1));
        assertThat(comparator.compare(p2, p1), is(1));
        assertThat(comparator.compare(p1, p1), is(0));
        assertThat(comparator.compare(thisPoint, p1), is(-1));
        assertThat(comparator.compare(p2, thisPoint), is(1));
        assertThat(comparator.compare(p1, new Point(0, 3)), is(-1));
        assertThat(comparator.compare(p1, new Point(3, 0)), is(1));
    }

    @Test
    public void compareTo() throws Exception
    {
        initPoints(0, 1, 1, 2);
        assertThat(thisPoint, lessThan(thatPoint));
        initPoints(0, 1, 2, 1);
        assertThat(thisPoint, lessThan(thatPoint));

        initPoints(1, 3, 0, 2);
        assertThat(thisPoint, greaterThan(thatPoint));
        initPoints(3, 5, -1, 5);
        assertThat(thisPoint, greaterThan(thatPoint));

        initPoints(1, 2, 1, 2);
        assertThat(thisPoint, comparesEqualTo(thatPoint));
        assertThat(thisPoint, comparesEqualTo(thisPoint));
    }

    private void initPoints(final int x0, final int y0, final int x1, final int y1)
    {
        thisPoint = new Point(x0, y0);
        thatPoint = new Point(x1, y1);
    }
}