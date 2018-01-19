import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board
{
    private final int[][] boardBlocks;
    private final int blankRow, blankCol;

    public Board(final int[][] blocks)
    {
        boardBlocks = new int[blocks.length][blocks.length];

        int blRow = 0, blCol = 0;
        for (int row = 0; row < blocks.length; row++)
        {
            for (int col = 0; col < blocks.length; col++)
            {
                boardBlocks[row][col] = blocks[row][col];

                if (blocks[row][col] == 0)
                {
                    blRow = row;
                    blCol = col;
                }
            }
        }
        blankRow = blRow;
        blankCol = blCol;
    }

    public int dimension()
    {
        return boardBlocks.length;
    }

    public int hamming()
    {
        int hamming = 0;
        for (int row = 0; row < dimension(); row++)
        {
            for (int col = 0; col < dimension(); col++)
            {
                if (isCellMisplaced(row, col))
                {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan()
    {
        int manhattan = 0;
        for (int row = 0; row < dimension(); row++)
        {
            for (int col = 0; col < dimension(); col++)
            {
                if (isCellMisplaced(row, col))
                {
                    manhattan += manhattanDistanceFor(row, col);
                }

            }
        }
        return manhattan;
    }

    private boolean isCellMisplaced(final int row, final int col)
    {
        return boardBlocks[row][col] != 0 && get1DIndex(row, col) + 1 != boardBlocks[row][col];
    }

    public boolean isGoal()
    {
        return hamming() == 0;
    }

    public Board twin()
    {
        final int[][] twinBlocks = getCopy();
        final int swapRow = blankRow != 0 ? 0 : 1;

        swap(twinBlocks, swapRow, 0, swapRow, 1);

        return new Board(twinBlocks);
    }

    @Override
    public boolean equals(final Object y)
    {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        final Board board = (Board) y;

        return Arrays.deepEquals(boardBlocks, board.boardBlocks);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        final String format = "%" + (String.valueOf(dimension() * dimension() - 1).length() + 1) + "d";
        sb.append(dimension());

        Arrays.stream(boardBlocks)
            .peek(row -> sb.append("\n"))
            .flatMapToInt(Arrays::stream)
            .forEach(cell -> sb.append(String.format(format, cell)));

        sb.append("\n");
        return sb.toString();
    }

    public Iterable<Board> neighbors()
    {
        final boolean[] configs =
            {
                withinRange(blankRow - 1),
                withinRange(blankCol - 1),
                withinRange(blankRow + 1),
                withinRange(blankCol + 1)
            };
        final List<Board> boards = new ArrayList<>(4);

        for (int i = 0; i < configs.length; i++)
        {
            if (configs[i])
            {
                final int[][] copy = getCopy();
                setupNeighborBlocks(copy, i);
                boards.add(new Board(copy));
            }
        }

        return boards;
    }

    private int[][] getCopy()
    {
        return Arrays.stream(boardBlocks)
            .map(row -> Arrays.copyOf(row, dimension()))
            .toArray(int[][]::new);
    }

    private void setupNeighborBlocks(final int[][] copy, final int option)
    {
        if (option == 0)
        {
            swap(copy, blankRow, blankCol, blankRow - 1, blankCol);
        }
        if (option == 1)
        {
            swap(copy, blankRow, blankCol, blankRow, blankCol - 1);
        }
        if (option == 2)
        {
            swap(copy, blankRow, blankCol, blankRow + 1, blankCol);
        }
        if (option == 3)
        {
            swap(copy, blankRow, blankCol, blankRow, blankCol + 1);
        }
    }

    private int get1DIndex(final int row, final int col)
    {
        return row * dimension() + col;
    }

    private int manhattanDistanceFor(final int row, final int col)
    {
        final int cell = boardBlocks[row][col] - 1,
            goalRow = cell / dimension(),
            goalCol = cell - goalRow * dimension();

        return Math.abs(goalRow - row) + Math.abs(goalCol - col);
    }

    private boolean withinRange(final int index)
    {
        return index < dimension() && index >= 0;
    }

    private void swap(final int[][] blocks, final int srcRow, final int srcCol,
                      final int trgRow, final int trgCol)
    {
        final int temp = blocks[srcRow][srcCol];
        blocks[srcRow][srcCol] = blocks[trgRow][trgCol];
        blocks[trgRow][trgCol] = temp;
    }
}
