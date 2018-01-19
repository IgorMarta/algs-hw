import java.util.ArrayList;
import java.util.List;

public class CircularSuffixArray
{
    private final List<Integer> indexArray;

    public CircularSuffixArray(final String s)
    {
        if (s == null)
        {
            throw new IllegalArgumentException();
        }

        final char[] chars = s.toCharArray();
        indexArray = new ArrayList<>(chars.length);

        for (int index = 0; index < chars.length; index++)
        {
            indexArray.add(index);
        }

        indexArray.sort((lhs, rhs) -> compareSuffixes(chars, lhs, rhs));
    }

    public int length()
    {
        return indexArray.size();
    }

    public int index(final int i)
    {
        if (i < 0 || i >= length())
        {
            throw new IllegalArgumentException();
        }
        return indexArray.get(i);
    }

    private int compareSuffixes(final char[] chars, final int lhs, final int rhs)
    {
        int i = circularIndex(lhs, chars.length),
            j = circularIndex(rhs, chars.length);

        for (int index = 0; chars[i] == chars[j] && index < chars.length; index++)
        {
            i = circularIndex(i + 1, chars.length);
            j = circularIndex(j + 1, chars.length);
        }

        return Character.compare(chars[i], chars[j]);
    }

    private int circularIndex(final int index, final int upperBound)
    {
        return index < upperBound ? index : 0;
    }
}
