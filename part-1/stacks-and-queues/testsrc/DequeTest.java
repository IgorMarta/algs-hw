import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class DequeTest
{
    private Deque<String> deque;

    @Before
    public void setup()
    {
        deque = new Deque<>();
    }

    @Test
    public void dequeIsEmpty()
    {
        assertThat(deque.isEmpty(), is(true));
        deque.addFirst("first");
        assertThat(deque.isEmpty(), is(false));
        deque.addLast("last");
        assertThat(deque.isEmpty(), is(false));
        deque.removeFirst();
        assertThat(deque.isEmpty(), is(false));
        deque.removeLast();
        assertThat(deque.isEmpty(), is(true));
    }

    @Test
    public void dequeSize()
    {
        assertThat(deque.size(), is(0));
        deque.addFirst("first");
        assertThat(deque.size(), is(1));
        deque.addLast("last");
        assertThat(deque.size(), is(2));
        deque.removeFirst();
        assertThat(deque.size(), is(1));
        deque.removeLast();
        assertThat(deque.size(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void dequeAddFirstNull()
    {
        deque.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dequeAddLastNull()
    {
        deque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeRemoveFirstEmpty()
    {
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeRemoveLastEmpty()
    {
        deque.removeLast();
    }

    @Test
    public void iteratorNotNull()
    {
        assertThat(deque.iterator(), notNullValue(Iterator.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iteratorRemoveNotSupported()
    {
        deque.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorOutOfBounds()
    {
        deque.iterator().next();
    }

    @Test
    public void iteratorNext()
    {
        final String first = "first", last = "last";

        deque.addFirst(first);
        deque.addLast(last);
        assertThat(deque, contains(first, last));

        assertThat(deque.removeFirst(), is(first));
        assertThat(deque, contains(last));

        assertThat(deque.removeLast(), is(last));
        assertThat(deque, emptyIterable());
    }

    @Test
    public void iteratorHasNext()
    {
        assertThat(deque.iterator().hasNext(), is(false));
        deque.addFirst("first");
        assertThat(deque.iterator().hasNext(), is(true));
        deque.addLast("last");
        assertThat(deque.iterator().hasNext(), is(true));
        deque.removeFirst();
        assertThat(deque.iterator().hasNext(), is(true));
        deque.removeLast();
        assertThat(deque.iterator().hasNext(), is(false));
    }

    @Test
    public void testNestedForeach()
    {
        final String[] ints = IntStream.range(0, 100)
            .mapToObj(String::valueOf)
            .peek(deque::addLast)
            .toArray(String[]::new);

        for (final String item : deque)
        {
            int i = 0;
            final String[] assertions = new String[deque.size()];
            for (final String str : deque)
            {
                assertions[i++] = str;
            }
            assertThat(assertions, arrayContainingInAnyOrder(ints));
        }
    }
}