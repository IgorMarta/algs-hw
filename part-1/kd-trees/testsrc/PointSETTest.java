import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PointSETTest
{
    @Test
    public void isEmpty()
    {
        final PointSET pointSET = new PointSET();

        assertThat(pointSET.isEmpty(), is(true));
        pointSET.insert(new Point2D(.2, .5));
        assertThat(pointSET.isEmpty(), is(false));
    }

    @Test
    public void size()
    {
        final PointSET pointSET = new PointSET();

        assertThat(pointSET.size(), is(0));
        pointSET.insert(new Point2D(.2, .2));
        assertThat(pointSET.size(), is(1));
    }

    @Test
    public void insert()
    {
        final Point2D point = new Point2D(.1, .1);
        final PointSET pointSET = new PointSET();

        pointSET.insert(point);
        assertThat(pointSET.contains(point), is(true));
        assertThat(pointSET.size(), is(1));
        pointSET.insert(point);
        assertThat(pointSET.size(), is(1));
        pointSET.insert(new Point2D(.1, .1));
        assertThat(pointSET.size(), is(1));
        pointSET.insert(new Point2D(.1, .2));
        assertThat(pointSET.size(), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNull()
    {
        new PointSET().insert(null);
    }

    @Test
    public void contains()
    {
        final Point2D point = new Point2D(.1, .1);
        final PointSET pointSET = new PointSET();

        assertThat(pointSET.contains(point), is(false));
        pointSET.insert(point);
        assertThat(pointSET.contains(point), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsNull()
    {
        new PointSET().contains(null);
    }

    @Test
    public void range()
    {
        final RectHV rect = new RectHV(.1, .1, .5, .5);
        final PointSET pointSET = new PointSET();
        final Point2D[] pointsInside = {
            new Point2D(.1, .1), new Point2D(.2, .2), new Point2D(.4, .2)
        };
        final Point2D[] pointsOutside = {
            new Point2D(0, .1), new Point2D(.6, .3), new Point2D(.4, .6)
        };

        Arrays.stream(pointsInside)
            .forEach(pointSET::insert);
        Arrays.stream(pointsOutside)
            .forEach(pointSET::insert);

        assertThat(pointSET.range(rect), containsInAnyOrder(pointsInside));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeNull()
    {
        new PointSET().range(null);
    }

    @Test
    public void nearest()
    {
        final PointSET pointSET = new PointSET();
        final Point2D point = new Point2D(.54, .54);
        final Point2D[] points = {
            new Point2D(0, 0), new Point2D(.25, .25), new Point2D(.5, .5),
            new Point2D(.6, .6), new Point2D(.75, .75), new Point2D(.9, .9)
        };

        assertThat(pointSET.nearest(point), nullValue());

        Arrays.stream(points)
            .forEach(pointSET::insert);

        assertThat(pointSET.nearest(point), equalTo(new Point2D(.5, .5)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nearestNull()
    {
        new PointSET().nearest(null);
    }
}