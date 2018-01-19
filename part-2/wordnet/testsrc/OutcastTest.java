import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OutcastTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArg()
    {
        new Outcast(null);
    }

    @Test
    public void outcast()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");
        final Outcast outcast = new Outcast(wordNet);
        final String[] nouns = {"c", "e", "g", "b"};

        assertThat(outcast.outcast(nouns), is("e"));
    }
}