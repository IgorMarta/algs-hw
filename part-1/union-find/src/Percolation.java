import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private static final int TOP_VIRTUAL_SITE = 0;
    private final int bottomVirtualSite, gridSize;
    private final WeightedQuickUnionUF wqUnionUF, wqUnionWithoutVB;
    private final boolean[] percolationGrid;
    private int openSites = 0;

    public Percolation(final int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("n must be > 0");
        }

        percolationGrid = new boolean[n * n];
        gridSize = n;
        bottomVirtualSite = n * n + 1;
        wqUnionUF = new WeightedQuickUnionUF(n * n + 2);
        wqUnionWithoutVB = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(final int row, final int col)
    {
        if (!isOpen(row, col))
        {
            connectToOpenNeighbours(row, col);
            percolationGrid[translateIndices(row, col)] = true;
            connectVirtualSite(row, col);
            openSites++;
        }
    }

    public boolean isOpen(final int row, final int col)
    {
        validateIndices(row, col);
        return percolationGrid[translateIndices(row, col)];
    }

    public boolean isFull(final int row, final int col)
    {
        validateIndices(row, col);
        return wqUnionWithoutVB.connected(TOP_VIRTUAL_SITE, translateIndices(row, col) + 1);
    }

    public int numberOfOpenSites()
    {
        return openSites;
    }

    public boolean percolates()
    {
        return openSites > 0 && wqUnionUF.connected(TOP_VIRTUAL_SITE, bottomVirtualSite);
    }

    private void connectVirtualSite(final int row, final int col)
    {
        if (row == 1)
        {
            wqUnionUF.union(TOP_VIRTUAL_SITE, translateIndices(row, col) + 1);
            wqUnionWithoutVB.union(TOP_VIRTUAL_SITE, translateIndices(row, col) + 1);
        }
        if (row == gridSize)
        {
            wqUnionUF.union(bottomVirtualSite, translateIndices(row, col) + 1);
        }
    }

    private void connectToOpenNeighbours(final int row, final int col)
    {
        connectNeighbour(row, col, row - 1, col);
        connectNeighbour(row, col, row, col - 1);
        connectNeighbour(row, col, row + 1, col);
        connectNeighbour(row, col, row, col + 1);
    }

    private void connectNeighbour(final int row, final int col, final int nRow, final int nCol)
    {
        if (withinRange(nRow) && withinRange(nCol) && isOpen(nRow, nCol))
        {
            wqUnionUF.union(translateIndices(nRow, nCol) + 1, translateIndices(row, col) + 1);
            wqUnionWithoutVB.union(translateIndices(nRow, nCol) + 1, translateIndices(row, col) + 1);
        }
    }

    private void validateIndices(final int row, final int col)
    {
        if (!withinRange(row) || !withinRange(col))
        {
            throw new IllegalArgumentException("Indices are out of bounds.");
        }
    }

    private boolean withinRange(final int index)
    {
        return index > 0 && index <= gridSize;
    }

    private int translateIndices(final int row, final int col)
    {
        return decrement(row) * gridSize + decrement(col);
    }

    private int decrement(final int index)
    {
        return index - 1;
    }
}
