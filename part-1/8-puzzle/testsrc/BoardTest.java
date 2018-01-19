import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class BoardTest
{
    @Test
    public void dimension()
    {
        final int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        final Board board = new Board(blocks);

        assertThat(board.dimension(), is(blocks.length));
    }

    @Test
    public void hamming()
    {
        final int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        final Board board = new Board(blocks);

        assertThat(board.hamming(), is(5));
    }

    @Test
    public void manhattan()
    {
        final int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        final Board board = new Board(blocks);

        assertThat(board.manhattan(), is(10));
    }

    @Test
    public void isGoal()
    {
        assertThat(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}).isGoal(),
            is(true));
        assertThat(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}}).isGoal(),
            is(false));
    }

    @Test
    public void twin()
    {
        final int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        final Board board = new Board(blocks);

        assertThat(board.twin().hamming(), is(2));
    }

    @Test
    public void _equals()
    {
        final int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        final int[][] blocks2 = {{1, 3, 2}, {4, 5, 6}, {7, 8, 0}};
        final Board board = new Board(blocks);

        assertThat(board, equalTo(board));
        assertThat(board, not(equalTo(null)));
        assertThat(board, equalTo(new Board(blocks)));
        assertThat(board, not(equalTo(new Board(blocks2))));
    }

    @Test
    public void neighbors()
    {
        final int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        final Board board = new Board(blocks);

        assertThat(board.neighbors(), hasItems(
            new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}}),
            new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}}),
            new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}}),
            new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}})));

        final int[][] blocks2 = {{8, 1, 3}, {4, 2, 5}, {7, 6, 0}};
        final Board board2 = new Board(blocks2);

        assertThat(board2.neighbors(), hasItems(
            new Board(new int[][]{{8, 1, 3}, {4, 2, 5}, {7, 0, 6}}),
            new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}})));

        final int[][] blocks3 = {{8, 1, 3}, {4, 6, 2}, {7, 0, 5}};
        final Board board3 = new Board(blocks3);

        assertThat(board3.neighbors(), hasItems(
            new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}),
            new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}}),
            new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {0, 7, 5}})));
    }

    @Test
    public void _toString()
    {
        final String expected = "3\n" +
            " 8 1 3\n" +
            " 0 4 2\n" +
            " 7 6 5\n";
        assertThat(new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}}).toString(), is(expected));
    }
}