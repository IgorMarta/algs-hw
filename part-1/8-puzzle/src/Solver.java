import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Comparator;

public class Solver
{
    private final Node solutionNode;

    public Solver(final Board board)
    {
        if (board == null)
        {
            throw new IllegalArgumentException();
        }

        final Comparator<Node> manhattanPriorityOrder = Comparator.comparingInt(Node::getManhattanPriority);
        final MinPQ<Node> originalBoardPQ = new MinPQ<>(manhattanPriorityOrder),
            twinBoardPQ = new MinPQ<>(manhattanPriorityOrder);

        originalBoardPQ.insert(new Node(board, 0, null));
        twinBoardPQ.insert(new Node(board.twin(), 0, null));

        while (notSolved(originalBoardPQ.min(), twinBoardPQ.min()))
        {
            insertNeighborsFor(originalBoardPQ);
            insertNeighborsFor(twinBoardPQ);
        }

        solutionNode = originalBoardPQ.delMin();
    }

    public boolean isSolvable()
    {
        return isSolutionNode(solutionNode);
    }

    public int moves()
    {
        return isSolvable() ? solutionNode.moves : -1;
    }

    public Iterable<Board> solution()
    {
        return isSolvable() ? solutionIterable() : null;
    }

    private boolean notSolved(final Node originalNode, final Node twinNode)
    {
        return !isSolutionNode(originalNode) && !isSolutionNode(twinNode);
    }

    private boolean isSolutionNode(final Node node)
    {
        return node.board.isGoal();
    }

    private void insertNeighborsFor(final MinPQ<Node> queue)
    {
        final Node node = queue.delMin();
        for (final Board neighbor : node.board.neighbors())
        {
            if (node.prev == null || !neighbor.equals(node.prev.board))
            {
                queue.insert(new Node(neighbor, node.moves + 1, node));
            }
        }
    }

    private Iterable<Board> solutionIterable()
    {
        final ArrayDeque<Board> solutionDeque = new ArrayDeque<>(moves() + 1);

        for (Node node = solutionNode; node != null; node = node.prev)
        {
            solutionDeque.add(node.board);
        }

        return solutionDeque::descendingIterator;
    }

    private static class Node
    {
        private final Board board;
        private final int moves;
        private final int manhattanPriority;
        private final Node prev;

        private Node(final Board board, final int moves, final Node prev)
        {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            manhattanPriority = board.manhattan() + moves;
        }

        int getManhattanPriority()
        {
            return manhattanPriority;
        }
    }

    public static void main(final String[] args)
    {
        final In in = new In(args[0]);
        final int n = in.readInt();
        final int[][] blocks = new int[n][n];

        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                blocks[row][col] = in.readInt();
            }
        }

        final Board initial = new Board(blocks);
        final Solver solver = new Solver(initial);

        if (!solver.isSolvable())
        {
            StdOut.println("No solution possible");
        }
        else
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (final Board board : solver.solution())
            {
                StdOut.println(board);
            }
        }
    }
}
