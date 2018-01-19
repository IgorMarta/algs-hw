import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class Outcast
{
    private final WordNet wordnet;

    public Outcast(final WordNet wordnet)
    {
        validateArgs(wordnet);
        this.wordnet = wordnet;
    }

    public String outcast(final String[] nouns)
    {
        return Arrays.stream(nouns)
            .max(Comparator.comparingInt(noun -> computeSumFor(noun, nouns)))
            .orElseThrow(IllegalArgumentException::new);
    }

    private int computeSumFor(final String targetNoun, final String[] nouns)
    {
        return Arrays.stream(nouns)
            .mapToInt(noun -> wordnet.distance(targetNoun, noun))
            .sum();
    }

    private void validateArgs(final Object... args)
    {
        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull))
        {
            throw new IllegalArgumentException();
        }
    }
}
