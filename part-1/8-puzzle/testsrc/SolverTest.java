import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class SolverTest
{
    @Test(expected = IllegalArgumentException.class)
    public void nullBoard()
    {
        new Solver(null);
    }

    @Test
    public void isSolvable()
    {
        final Board solvable = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        final Board unsolvable = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});

        assertThat(new Solver(solvable).isSolvable(), is(true));
        assertThat(new Solver(unsolvable).isSolvable(), is(false));
    }

    @Test
    public void moves()
    {
        final Board solvable = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        final Board unsolvable = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});

        assertThat(new Solver(solvable).moves(), is(4));
        assertThat(new Solver(unsolvable).moves(), is(-1));
    }

    @Test
    public void solution()
    {
        final Board solvable = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        final Board unsolvable = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});

        assertThat(new Solver(solvable).solution(), contains(
            new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}),
            new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}}),
            new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}}),
            new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}),
            new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}})));

        assertThat(new Solver(unsolvable).solution(), nullValue());
    }
}