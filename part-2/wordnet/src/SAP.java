import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class SAP
{
    private final Digraph digraph;

    public SAP(final Digraph G)
    {
        validateArgs(G);
        digraph = new Digraph(G);
    }

    public int length(final int v, final int w)
    {
        validateArgs(v, w);
        return getSAPTuple(Collections.singletonList(v), Collections.singletonList(w))[0];
    }

    public int ancestor(final int v, final int w)
    {
        validateArgs(v, w);
        return getSAPTuple(Collections.singletonList(v), Collections.singletonList(w))[1];
    }

    public int length(final Iterable<Integer> v, final Iterable<Integer> w)
    {
        validateArgs(v, w);
        return getSAPTuple(v, w)[0];
    }

    public int ancestor(final Iterable<Integer> v, final Iterable<Integer> w)
    {
        validateArgs(v, w);
        return getSAPTuple(v, w)[1];
    }

    private int[] getSAPTuple(final Iterable<Integer> v, final Iterable<Integer> w)
    {
        validateArgs(v, w);

        final BreadthFirstDirectedPaths pathsForV = new BreadthFirstDirectedPaths(digraph, v);
        final BreadthFirstDirectedPaths pathsForW = new BreadthFirstDirectedPaths(digraph, w);
        int shortestDistance = -1;
        int ancestor = -1;

        for (int index = 0; index < digraph.V(); index++)
        {
            if (pathsForV.hasPathTo(index) && pathsForW.hasPathTo(index))
            {
                final int distance = pathsForV.distTo(index) + pathsForW.distTo(index);
                if (shortestDistance < 0 || shortestDistance > distance)
                {
                    ancestor = index;
                    shortestDistance = distance;
                }
            }
        }

        return new int[]{shortestDistance,  ancestor};
    }

    private void validateArgs(final Object... args)
    {
        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull))
        {
            throw new IllegalArgumentException();
        }
    }

    private void validateArgs(int v, int w)
    {
        if (outOfRange(v) || outOfRange(w))
        {
            throw new IllegalArgumentException();
        }
    }

    private boolean outOfRange(int index)
    {
        return index < 0 || index >= digraph.V();
    }
}
