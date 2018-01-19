import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation
{
    public static void main(final String[] args)
    {
        validateArgs(args);

        final int kSize = Integer.parseInt(args[0]);
        final RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        for (int sampleSize = 0; !StdIn.isEmpty(); sampleSize++)
        {
            enqueueRandomly(randomizedQueue, kSize, sampleSize, StdIn.readString());
        }

        randomizedQueue.forEach(System.out::println);
    }

    private static void enqueueRandomly(final RandomizedQueue<String> randomizedQueue, final int kSize,
                                        final int n, final String nextString)
    {
        if (randomizedQueue.size() < kSize)
        {
            randomizedQueue.enqueue(nextString);
        }
        else if (!randomizedQueue.isEmpty() && StdRandom.uniform() < kSize * 1.0 / n)
        {
            randomizedQueue.dequeue();
            randomizedQueue.enqueue(nextString);
        }
    }

    private static void validateArgs(final String[] args)
    {
        if (args.length != 1 || !args[0].matches("^\\d+$"))
        {
            throw new IllegalArgumentException("Client requires one integer parameter.");
        }
    }
}
