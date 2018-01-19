import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RandomizedQueueTest
{
    private RandomizedQueue<String> queue;

    @Before
    public void setup()
    {
        queue = new RandomizedQueue<>();
    }

    @Test
    public void queueIsEmpty()
    {
        assertThat(queue.isEmpty(), is(true));
        queue.enqueue("first");
        assertThat(queue.isEmpty(), is(false));
        queue.sample();
        assertThat(queue.isEmpty(), is(false));
        queue.dequeue();
        assertThat(queue.isEmpty(), is(true));
    }

    @Test
    public void queueSize()
    {
        assertThat(queue.size(), is(0));
        queue.enqueue("first");
        assertThat(queue.size(), is(1));
        queue.sample();
        assertThat(queue.size(), is(1));
        queue.dequeue();
        assertThat(queue.size(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void enqueueNull()
    {
        queue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueEmpty()
    {
        queue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void sampleEmpty()
    {
        queue.sample();
    }

    @Test
    public void iteratorNotNull()
    {
        assertThat(queue.iterator(), notNullValue(Iterator.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iteratorRemoveNotSupported()
    {
        queue.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorOutOfBounds()
    {
        queue.iterator().next();
    }

    @Test
    public void iteratorNext()
    {
        final String[] data = IntStream.range(0, 100)
            .mapToObj(String::valueOf)
            .peek(queue::enqueue)
            .toArray(String[]::new);

        assertThat(queue, containsInAnyOrder(data));

        final String sample = queue.sample();

        assertThat(queue, hasItem(sample));

        final String randomEntry = queue.dequeue();

        assertThat(queue, not(hasItem(randomEntry)));
    }

    @Test
    public void iteratorHasNext()
    {
        assertThat(queue.iterator().hasNext(), is(false));
        queue.enqueue("first");
        assertThat(queue.iterator().hasNext(), is(true));
        queue.sample();
        assertThat(queue.iterator().hasNext(), is(true));
        queue.dequeue();
        assertThat(queue.iterator().hasNext(), is(false));
    }

    @Test
    public void testNestedForeach()
    {
        final String[] ints = IntStream.range(0, 100)
            .mapToObj(String::valueOf)
            .peek(queue::enqueue)
            .toArray(String[]::new);

        for (final String item : queue)
        {
            int i = 0;
            final String[] assertions = new String[queue.size()];
            for (final String str : queue)
            {
                assertions[i++] = str;
            }
            assertThat(assertions, not(arrayContaining(ints)));
        }
    }
}