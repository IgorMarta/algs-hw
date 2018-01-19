import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PercolationTest
{
    @Test(expected = IllegalArgumentException.class)
    public void invalidGridSize()
    {
        new Percolation(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOpenInvalidLowerBoundArg()
    {
        percolation8().isOpen(0, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOpenInvalidUpperBoundArg()
    {
        percolation8().isOpen(3, 9);
    }

    @Test
    public void isOpen()
    {
        final Percolation percolation8 = percolation8();

        assertThat(percolation8.isOpen(1, 3), is(true));
        assertThat(percolation8.isOpen(8, 8), is(false));
        assertThat(percolation8.isOpen(5, 4), is(true));
        assertThat(percolation8.isOpen(4, 7), is(true));
        assertThat(percolation8.isOpen(8, 7), is(false));
        assertThat(percolation8.isOpen(7, 3), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isFullInvalidLowerBoundArg()
    {
        percolation8().isOpen(0, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isFullInvalidUpperBoundArg()
    {
        percolation8().isOpen(3, 9);
    }

    @Test
    public void isFull()
    {
        final Percolation percolation8 = percolation8();

        assertThat(percolation8.isFull(1, 3), is(true));
        assertThat(percolation8.isFull(1, 1), is(false));
        assertThat(percolation8.isFull(4, 4), is(false));
        assertThat(percolation8.isFull(8, 6), is(true));
        assertThat(percolation8.isFull(8, 5), is(false));
        assertThat(percolation8.isFull(7, 1), is(false));
    }

    @Test
    public void numberOfOpenSites()
    {
        final Percolation percolation = new Percolation(10);

        assertThat(percolation.numberOfOpenSites(), is(0));
        percolation.open(3, 4);
        assertThat(percolation.numberOfOpenSites(), is(1));
        percolation.open(5, 5);
        assertThat(percolation.numberOfOpenSites(), is(2));
        percolation.open(3, 4);
        assertThat(percolation.numberOfOpenSites(), is(2));
        assertThat(percolation8().numberOfOpenSites(), is(34));
    }

    @Test
    public void percolates()
    {
        final Percolation percolation = new Percolation(10);

        assertThat(percolation.percolates(), is(false));
        percolation.open(1, 5);
        assertThat(percolation.percolates(), is(false));
        assertThat(percolation8().percolates(), is(true));
        assertThat(percolation8No().percolates(), is(false));
    }

    private Percolation percolation8()
    {
        return readInputData("percolation/input8.txt");
    }

    private Percolation percolation8No()
    {
        return readInputData("percolation/input8-no.txt");
    }

    private Percolation readInputData(final String filename)
    {
        final In in = new In(filename);
        final Percolation percolation = new Percolation(in.readInt());

        while (!in.isEmpty())
        {
            percolation.open(in.readInt(), in.readInt());
        }

        return percolation;
    }
}