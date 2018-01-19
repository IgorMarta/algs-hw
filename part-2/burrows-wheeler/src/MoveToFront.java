import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront
{
    private static final int R = 256;

    public static void encode()
    {
        final LinkedCharList asciiChars = initASCIIList();

        while (!BinaryStdIn.isEmpty())
        {
            final char inputChar = BinaryStdIn.readChar();
            BinaryStdOut.write(asciiChars.remove(inputChar), 8);
            asciiChars.addFirst(inputChar);
        }

        BinaryStdOut.close();
    }

    public static void decode()
    {
        final LinkedCharList asciiChars = initASCIIList();

        while (!BinaryStdIn.isEmpty())
        {
            final char character = asciiChars.removeAtIndex(BinaryStdIn.readChar());
            BinaryStdOut.write(character);
            asciiChars.addFirst(character);
        }

        BinaryStdOut.close();
    }

    private static LinkedCharList initASCIIList()
    {
        final LinkedCharList asciiChars = new LinkedCharList();
        for (char chr = 0; chr < R; chr++)
        {
            asciiChars.addLast(chr);
        }

        return asciiChars;
    }

    public static void main(final String[] args)
    {
        validate(args);
        if ("-".equals(args[0]))
        {
            encode();
        }
        else
        {
            decode();
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
