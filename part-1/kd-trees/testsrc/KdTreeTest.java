import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class KdTreeTest
{
    @Test
    public void isEmpty()
    {
        final KdTree kdTree = new KdTree();

        assertThat(kdTree.isEmpty(), is(true));
        kdTree.insert(new Point2D(.2, .5));
        assertThat(kdTree.isEmpty(), is(false));
    }

    @Test
    public void size()
    {
        final KdTree kdTree = new KdTree();

        assertThat(kdTree.size(), is(0));
        kdTree.insert(new Point2D(.2, .2));
        assertThat(kdTree.size(), is(1));
    }

    @Test
    public void insert()
    {
        final Point2D point = new Point2D(.1, .1);
        final KdTree kdTree = new KdTree();

        kdTree.insert(point);
        assertThat(kdTree.contains(point), is(true));
        assertThat(kdTree.size(), is(1));
        kdTree.insert(point);
        assertThat(kdTree.size(), is(1));
        kdTree.insert(new Point2D(.1, .1));
        assertThat(kdTree.size(), is(1));
        kdTree.insert(new Point2D(.1, .2));
        assertThat(kdTree.size(), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNull()
    {
        new KdTree().insert(null);
    }

    @Test
    public void contains()
    {
        final Point2D point = new Point2D(.1, .1);
        final KdTree kdTree = new KdTree();

        assertThat(kdTree.contains(point), is(false));
        kdTree.insert(point);
        assertThat(kdTree.contains(point), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsNull()
    {
        new KdTree().contains(null);
    }

    @Test
    public void range()
    {
        final RectHV rect = new RectHV(.1, .1, .5, .5);
        final KdTree kdTree = new KdTree();
        final Point2D[] pointsInside = {
            new Point2D(.1, .1), new Point2D(.2, .2), new Point2D(.4, .2)
        };
        final Point2D[] pointsOutside = {
            new Point2D(0, .1), new Point2D(.6, .3), new Point2D(.4, .6)
        };

        Arrays.stream(pointsInside)
            .forEach(kdTree::insert);
        Arrays.stream(pointsOutside)
            .forEach(kdTree::insert);

        assertThat(kdTree.range(rect), both(hasItems(pointsInside))
                .and(not(hasItems(pointsOutside))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeNull()
    {
        new KdTree().range(null);
    }

    @Test
    public void nearest()
    {
        final KdTree kdTree = new KdTree();
        final Point2D point = new Point2D(.54, .54);
        final Point2D[] points = {
            new Point2D(0, 0), new Point2D(.25, .25), new Point2D(.5, .5),
            new Point2D(.6, .6), new Point2D(.75, .75), new Point2D(.9, .9)
        };

        assertThat(kdTree.nearest(point), nullValue());

        Arrays.stream(points)
            .forEach(kdTree::insert);

        assertThat(kdTree.nearest(point), equalTo(new Point2D(.5, .5)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nearestNull()
    {
        new KdTree().nearest(null);
    }
}