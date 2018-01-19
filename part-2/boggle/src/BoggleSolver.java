import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoggleSolver
{
    private final StringTrieST dictionary = new StringTrieST();

    public BoggleSolver(final String[] dictionary)
    {
        for (final String word : dictionary)
        {
            if (word.length() > 2)
            {
                this.dictionary.put(word, word);
            }
        }
    }

    public Iterable<String> getAllValidWords(final BoggleBoard board)
    {
        final Set<String> words = new HashSet<>();

        for (int row = 0; row < board.rows(); row++)
        {
            for (int col = 0; col < board.cols(); col++)
            {
                final String word = toWord(board.getLetter(row, col));
                final StringTrieST.Node node = dictionary.nodeWithPrefix(word, null, 0);

                if (node != null)
                {
                    final boolean[] visited = new boolean[board.rows() * board.cols()];
                    visited[getIndex(row, col, board.cols())] = true;
                    findPathWords(board, new DieSide(row, col, word, node, visited), words);
                }
            }
        }
        return words;
    }

    public int scoreOf(final String word)
    {
        return getScoringConfig().entrySet()
            .stream()
            .filter(entry -> byLength(word, entry.getValue()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(0);
    }

    private void findPathWords(final BoggleBoard board, final DieSide rootSide, final Set<String> words)
    {
        final Queue<DieSide> queue = new Queue<>();
        queue.enqueue(rootSide);

        while (!queue.isEmpty())
        {
            final DieSide dieSide = queue.dequeue();
            if (dictionary.contains(dieSide.pathWord))
            {
                words.add(dieSide.pathWord);
            }

            for (final DieSide neighbor : adjacent(dieSide, board))
            {
                queue.enqueue(neighbor);
            }
        }
    }

    private int getIndex(final int row, final int col, final int cols)
    {
        return row * cols + col;
    }

    private List<DieSide> adjacent(final DieSide dieSide, final BoggleBoard board)
    {
        final List<DieSide> adjList = new ArrayList<>();

        if (dieSide.row > 0)
        {
            addNeighbor(dieSide, board, -1, 0, adjList);
            if (dieSide.col > 0)
            {
                addNeighbor(dieSide, board, -1, -1, adjList);
            }
        }

        if (dieSide.col > 0)
        {
            addNeighbor(dieSide, board, 0, -1, adjList);
            if (dieSide.row < board.rows() - 1)
            {
                addNeighbor(dieSide, board, 1, -1, adjList);
            }
        }

        if (dieSide.row < board.rows() - 1)
        {
            addNeighbor(dieSide, board, 1, 0, adjList);
            if (dieSide.col < board.cols() - 1)
            {
                addNeighbor(dieSide, board, 1, 1, adjList);
            }
        }

        if (dieSide.col < board.cols() - 1)
        {
            addNeighbor(dieSide, board, 0, 1, adjList);
            if (dieSide.row > 0)
            {
                addNeighbor(dieSide, board, -1, 1, adjList);
            }
        }

        return adjList;
    }

    private void addNeighbor(final DieSide dieSide, final BoggleBoard board, final int rowGap, final int colGap,
                             final List<DieSide> list)
    {
        final int newRow = dieSide.row + rowGap, newCol = dieSide.col + colGap;
        final String suffix = toWord(board.getLetter(newRow, newCol));
        final String word = dieSide.pathWord + suffix;
        final StringTrieST.Node node = dictionary.nodeWithPrefix(word, dieSide.node, suffix.length());

        if (node != null && !dieSide.visited[getIndex(newRow, newCol, board.cols())])
        {
            final boolean[] visited = Arrays.copyOf(dieSide.visited, dieSide.visited.length);
            visited[getIndex(newRow, newCol, board.cols())] = true;
            list.add(new DieSide(newRow, newCol, word, node, visited));
        }
    }

    private Map<Integer, Predicate<String>> getScoringConfig()
    {
        final Map<Integer, Predicate<String>> scoreMap = new HashMap<>();

        scoreMap.put(0, word -> word == null || word.length() < 3);
        scoreMap.put(1, word -> word.length() > 2 && word.length() < 5);
        scoreMap.put(2, word -> word.length() == 5);
        scoreMap.put(3, word -> word.length() == 6);
        scoreMap.put(5, word -> word.length() == 7);
        scoreMap.put(11, word -> word.length() > 7);

        return scoreMap;
    }

    private boolean byLength(final String word, final Predicate<String> predicate)
    {
        return predicate.test(dictionary.get(word));
    }

    private String toWord(final char letter)
    {
        return 'Q' == letter ? "QU" : String.valueOf(letter);
    }

    private interface Predicate<T>
    {
        boolean test(T value);
    }

    private static class DieSide
    {
        private final int row;
        private final int col;
        private final String pathWord;
        private final StringTrieST.Node node;
        private final boolean[] visited;

        DieSide(final int row, final int col, final String pathWord, final StringTrieST.Node node, final boolean[] visited)
        {
            this.row = row;
            this.col = col;
            this.pathWord = pathWord;
            this.node = node;
            this.visited = visited;
        }
    }
}
