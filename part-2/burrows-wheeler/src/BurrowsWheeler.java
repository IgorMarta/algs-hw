import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler
{
    private static final int R = 256;

    public static void transform()
    {
        final String inputString = BinaryStdIn.readString();
        final CircularSuffixArray suffixArray = new CircularSuffixArray(inputString);
        int first = 0;

        while (suffixArray.index(first) != 0)
        {
            first++;
        }

        BinaryStdOut.write(first);

        for (int index = 0; index < suffixArray.length(); index++)
        {
            BinaryStdOut.write(inputString.charAt(getIndex(suffixArray, index)));
        }

        BinaryStdOut.close();
    }

    private static int getIndex(final CircularSuffixArray suffixArray, final int index)
    {
        return suffixArray.index(index) > 0 ? suffixArray.index(index) - 1 : suffixArray.length() - 1;
    }

    public static void inverseTransform()
    {
        final int first = BinaryStdIn.readInt();
        final char[] input = BinaryStdIn.readString().toCharArray(), firstColumn = new char[input.length];
        final int[] indexCount = countKeyIndices(input), next = new int[input.length];

        for (int index = 0; index < input.length; index++)
        {
            final int charIndex = indexCount[input[index]];
            firstColumn[charIndex] = input[index];
            next[charIndex] = index;
            indexCount[input[index]]++;
        }

        for (int counter = 0, row = first; counter < firstColumn.length; counter++, row = next[row])
        {
            BinaryStdOut.write(firstColumn[row]);
        }

        BinaryStdOut.close();
    }

    private static int[] countKeyIndices(final char[] input)
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
