import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeamCarver
{
    private int xSize, ySize;
    private int[][] rgbGrid;

    public SeamCarver(final Picture picture)
    {
        validateArg(picture);
        xSize = picture.width();
        ySize = picture.height();
        rgbGrid = new int[ySize][xSize];

        for (int row = 0; row < height(); row++)
        {
            for (int col = 0; col < width(); col++)
            {
                rgbGrid[row][col] = picture.getRGB(col, row);
            }
        }
    }

    public Picture picture()
    {
        final Picture picture = new Picture(width(), height());

        for (int row = 0; row < height(); row++)
        {
            for (int col = 0; col < width(); col++)
            {
                picture.setRGB(col, row, rgbGrid[row][col]);
            }
        }

        return picture;
    }

    public int width()
    {
        return xSize;
    }

    public int height()
    {
        return ySize;
    }

    public double energy(final int x, final int y)
    {
        withinRange(y, x);
        return insideBoundaries(y, height()) && insideBoundaries(x, width())
            ? Math.sqrt(sqDelta(y, x, ImagePath.HORIZONTAL) + sqDelta(y, x, ImagePath.VERTICAL))
            : 1000;
    }

    public int[] findHorizontalSeam()
    {
        return findSeamForPath(ImagePath.HORIZONTAL);
    }

    public int[] findVerticalSeam()
    {
        return findSeamForPath(ImagePath.VERTICAL);
    }

    public void removeHorizontalSeam(final int[] seam)
    {
        validateSeam(seam, ImagePath.HORIZONTAL);

        rgbGrid = getNewGrid(seam, ImagePath.HORIZONTAL);

        ySize--;
    }

    public void removeVerticalSeam(final int[] seam)
    {
        validateSeam(seam, ImagePath.VERTICAL);

        rgbGrid = getNewGrid(seam, ImagePath.VERTICAL);

        xSize--;
    }

    private int[] findSeamForPath(final ImagePath path)
    {
        final double[] energy = new double[height() * width() + 2];
        final double[] distTo = new double[height() * width() + 2];
        final DirectedEdge[] edgeTo = new DirectedEdge[height() * width() + 2];

        initEnergyGrid(path, energy, distTo);
        relaxEdges(path, distTo, edgeTo, energy);

        return getPath(path, edgeTo);
    }

    private int[][] getNewGrid(final int[] seam, final ImagePath path)
    {
        final int[][] newGrid = ImagePath.VERTICAL == path
            ? new int[height()][width() - 1]
            : new int[height() - 1][width()];

        final int height = pathRelativeHeight(path);
        final int width = pathRelativeWidth(path);

        for (int row = 0; row < height; row++)
        {
            int gap = 0;
            for (int col = 0; col < width; col++)
            {
                if (seam[row] != col)
                {
                    setGridCell(newGrid, row, col, gap, path);
                }
                else
                {
                    gap++;
                }
            }
        }
        return newGrid;
    }

    private void setGridCell(final int[][] newGrid, final int row, final int col, final int gap, final ImagePath path)
    {
        if (ImagePath.VERTICAL == path)
        {
            newGrid[row][col - gap] = rgbGrid[row][col];
        }
        else
        {
            newGrid[col - gap][row] = rgbGrid[col][row];
        }
    }

    private void initEnergyGrid(final ImagePath path, final double[] energy, final double[] distTo)
    {
        final int height = pathRelativeHeight(path);
        final int width = pathRelativeWidth(path);

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                energy[row * width + col + 1] = path == ImagePath.VERTICAL ? energy(col, row) : energy(row, col);
                distTo[row * width + col + 1] = Double.POSITIVE_INFINITY;
            }
        }

        energy[0] = 0;
        energy[height() * width() + 1] = 0;
        distTo[0] = 0;
        distTo[height() * width() + 1] = Double.POSITIVE_INFINITY;
    }

    private void relaxEdges(final ImagePath path, final double[] distTo, final DirectedEdge[] edgeTo, final double[] energy)
    {
        for (int index = 0; index < height() * width() + 2; index++)
        {
            adj(index, path, energy).forEach(edge -> relax(edge, distTo, edgeTo));
        }
    }

    private void relax(final DirectedEdge edge, final double[] distTo, final DirectedEdge[] edgeTo)
    {
        final int v = edge.from(), w = edge.to();
        if (distTo[w] > distTo[v] + edge.weight())
        {
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
        }
    }

    private int[] getPath(final ImagePath path, final DirectedEdge[] edgeTo)
    {
        final int height = pathRelativeHeight(path);
        final int[] seam = new int[height];
        int index = height;

        for (DirectedEdge edge = edgeTo[edgeTo.length - 1]; edge != null && index > 0; edge = edgeTo[edge.from()])
        {
            seam[--index] = pathRelativeCol(edge.from() - 1, path);
        }

        return seam;
    }

    private Iterable<DirectedEdge> adj(final int index, final ImagePath path, final double[] energy)
    {
        if (index >= height() * width() + 1)
        {
            return Collections.emptyList();
        }

        final int height = pathRelativeHeight(path);
        final int width = pathRelativeWidth(path);

        if (index == 0)
        {
            return fakeRootEdges(index, width, energy);
        }
        else if (index > width * (height - 1))
        {
            return fakeSinkEdge(index, height, width, energy);
        }
        else
        {
            return adjacentEdgesFor(index, path, width, energy);
        }
    }

    private List<DirectedEdge> fakeRootEdges(final int index, final int width, final double[] energy)
    {
        final List<DirectedEdge> adjList = new ArrayList<>();
        for (int i = 1; i <= width; i++)
        {
            adjList.add(new DirectedEdge(0, i, energy[index]));
        }

        return adjList;
    }

    private List<DirectedEdge> fakeSinkEdge(final int index, final int height, final int width, final double[] energy)
    {
        return Collections.singletonList(new DirectedEdge(index, width * height + 1, energy[index]));
    }

    private Iterable<DirectedEdge> adjacentEdgesFor(final int index, final ImagePath path, final int width, final double[] energy)
    {
        final List<DirectedEdge> adjList = new ArrayList<>();

        adjList.add(new DirectedEdge(index, index + width, energy[index]));
        if (pathRelativeCol(index - 1, path) > 0)
        {
            adjList.add(new DirectedEdge(index, index + width - 1, energy[index]));
        }
        if (pathRelativeCol(index - 1, path) < width - 1)
        {
            adjList.add(new DirectedEdge(index, index + width + 1, energy[index]));
        }
        return adjList;
    }

    private int pathRelativeHeight(final ImagePath path)
    {
        return path == ImagePath.VERTICAL ? height() : width();
    }

    private int pathRelativeWidth(final ImagePath path)
    {
        return path == ImagePath.VERTICAL ? width() : height();
    }

    private int pathRelativeCol(final int index, final ImagePath path)
    {
        return path == ImagePath.VERTICAL
            ? index - (width() * (index / width()))
            : index - (height() * (index / height()));
    }

    private double sqDelta(final int row, final int col, final ImagePath path)
    {
        return sqColorDelta(row, col, this::getRedValue, path)
            + sqColorDelta(row, col, this::getGreenValue, path)
            + sqColorDelta(row, col, this::getBlueValue, path);
    }

    private double sqColorDelta(final int row, final int col, final ColorFunction getter, final ImagePath path)
    {
        return path == ImagePath.VERTICAL
            ? Math.pow(getter.apply(rgbGrid[row][col + 1]) - getter.apply(rgbGrid[row][col - 1]), 2)
            : Math.pow(getter.apply(rgbGrid[row + 1][col]) - getter.apply(rgbGrid[row - 1][col]), 2);
    }

    private int getRedValue(final int rgb)
    {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreenValue(final int rgb)
    {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlueValue(final int rgb)
    {
        return rgb & 0xFF;
    }

    private void validateSeam(final int[] seam, final ImagePath path)
    {
        validateArg(seam);
        final int height = pathRelativeHeight(path);
        final int width = pathRelativeWidth(path);

        if (width <= 1 || seam.length != height)
        {
            throw new IllegalArgumentException();
        }

        for (int index = 0; index < seam.length; index++)
        {
            if (notWithinRange(seam[index], width) || invalidSeamSequence(seam, index))
            {
                throw new IllegalArgumentException();
            }
        }
    }

    private void validateArg(final Object arg)
    {
        if (arg == null)
        {
            throw new IllegalArgumentException();
        }
    }

    private void withinRange(final int row, final int col)
    {
        if (notWithinRange(row, height()) || notWithinRange(col, width()))
        {
            throw new IllegalArgumentException();
        }
    }

    private boolean invalidSeamSequence(final int[] seam, final int index)
    {
        return index + 1 < seam.length && Math.abs(seam[index] - seam[index + 1]) > 1;
    }

    private boolean notWithinRange(final int index, final int upperBound)
    {
        return index < 0 || index >= upperBound;
    }

    private boolean insideBoundaries(final int index, final int upperBound)
    {
        return index > 0 && index < upperBound - 1;
    }

    private enum ImagePath
    {
        VERTICAL, HORIZONTAL
    }

    private interface ColorFunction
    {
        int apply(int rgb);
    }
}
