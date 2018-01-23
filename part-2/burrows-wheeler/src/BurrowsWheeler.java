import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler
{
    private static final int R = 256;

    public static void transform()
    {
        final String inputString = BinaryStdIn.readString();
        final CircularSuffixArray suffixArray = new CircularSuffixArray(inputString);

        BinaryStdOut.write(getFirst(suffixArray));

        for (int index = 0; index < suffixArray.length(); index++)
        {
            BinaryStdOut.write(inputString.charAt(getIndex(suffixArray, index)));
        }

        BinaryStdOut.close();
    }

    private static int getFirst(final CircularSuffixArray suffixArray)
    {
        for (int first = 0; first < suffixArray.length(); first++)
        {
            if (suffixArray.index(first) == 0)
            {
                return first;
            }
        }
        throw new IllegalArgumentException();
    }

    private static int getIndex(final CircularSuffixArray suffixArray, final int index)
    {
        return suffixArray.index(index) > 0 ? suffixArray.index(index) - 1 : suffixArray.length() - 1;
    }

    public static void inverseTransform()
    {
        final int first = BinaryStdIn.readInt();
        final char[] input = BinaryStdIn.readString().toCharArray(), firstColumn = new char[input.length];
        final int[] next = new int[input.length];

        restoreFirstColumnAndNextIndices(input, firstColumn, next);

        for (int counter = 0, row = first; counter < firstColumn.length; counter++, row = next[row])
        {
            BinaryStdOut.write(firstColumn[row]);
        }

        BinaryStdOut.close();
    }

    private static void restoreFirstColumnAndNextIndices(final char[] input, final char[] firstColumn, final int[] next)
    {
        final int[] sortedIndices = getSortedIndices(input);
        for (int index = 0; index < input.length; index++)
        {
            final int charIndex = sortedIndices[input[index]];
            firstColumn[charIndex] = input[index];
            next[charIndex] = index;
            sortedIndices[input[index]]++;
        }
    }

    private static int[] getSortedIndices(final char[] input)
    {
        final int[] count = new int[R + 1];

        for (final char item : input)
        {
            count[item + 1]++;
        }

        for (int index = 0; index < R; index++)
        {
            count[index + 1] += count[index];
        }

        return count;
    }

    public static void main(final String[] args)
    {
        validate(args);
        if ("-".equals(args[0]))
        {
            transform();
        }
        else
        {
            inverseTransform();
        }
    }

    private static void validate(final String[] args)
    {
        if (args.length != 1 || !args[0].matches("^[+-]$"))
        {
            throw new IllegalArgumentException();
        }
    }
}
