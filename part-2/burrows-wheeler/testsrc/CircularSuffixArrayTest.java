import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CircularSuffixArrayTest
{
    @Test(expected = IllegalArgumentException.class)
    public void illegalArg()
    {
        new CircularSuffixArray(null);
    }

    @Test
    public void length()
    {
        assertThat(new CircularSuffixArray("").length(), is(0));
        assertThat(new CircularSuffixArray("aa").length(), is(2));
        assertThat(new CircularSuffixArray("qwerty").length(), is(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void indexInvalid()
    {
        new CircularSuffixArray("abc").index(5);
    }

    @Test
    public void index()
    {
        final CircularSuffixArray suffixArray = new CircularSuffixArray("ABRACADABRA!");

        assertThat(suffixArray.index(0), is(11));
        assertThat(suffixArray.index(2), is(7));
        assertThat(suffixArray.index(3), is(0));
        assertThat(suffixArray.index(7), is(1));
        assertThat(suffixArray.index(11), is(2));
    }
}