import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WordNetTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArgs()
    {
        new WordNet(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArg()
    {
        new WordNet("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForCycle3()
    {
        new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForMultipleRoots3()
    {
        new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidTwoRoots.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForCycle6()
    {
        new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForMultipleRoots6()
    {
        new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidTwoRoots.txt");
    }

    @Test
    public void nouns()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");

        assertThat(wordNet.nouns(), containsInAnyOrder("a", "b", "c", "d", "e", "f", "g", "h"));
    }

    @Test
    public void isNoun()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");

        assertThat(wordNet.isNoun("b"), is(true));
        assertThat(wordNet.isNoun("d"), is(true));
        assertThat(wordNet.isNoun("x"), is(false));
        assertThat(wordNet.isNoun("i"), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void distanceIllegalArg()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");

        wordNet.distance("x", "r");
    }

    @Test
    public void distance()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");

        assertThat(wordNet.distance("a", "e"), is(5));
        assertThat(wordNet.distance("b", "d"), is(2));
        assertThat(wordNet.distance("c", "a"), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sapIllegalArg()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");

        wordNet.sap("x", "r");
    }

    @Test
    public void sap()
    {
        final WordNet wordNet = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");

        assertThat(wordNet.sap("a", "e"), is("h"));
        assertThat(wordNet.sap("b", "d"), is("d"));
        assertThat(wordNet.sap("c", "a"), is("c"));
    }
}