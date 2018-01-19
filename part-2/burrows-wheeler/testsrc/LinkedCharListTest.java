import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LinkedCharListTest
{
    @Test
    public void addFirst()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        assertThat(linkedList.isEmpty(), is(true));
        assertThat(linkedList.size(), is(0));

        linkedList.addFirst('A');

        assertThat(linkedList.isEmpty(), is(false));
        assertThat(linkedList.size(), is(1));

        linkedList.addFirst('B');

        assertThat(linkedList.remove('B'), is(0));
    }

    @Test
    public void addLast()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        assertThat(linkedList.isEmpty(), is(true));
        assertThat(linkedList.size(), is(0));

        linkedList.addLast('A');

        assertThat(linkedList.isEmpty(), is(false));
        assertThat(linkedList.size(), is(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeAtIndexError()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        linkedList.removeAtIndex(3);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeAtIndexError2()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        linkedList.addLast('A');
        linkedList.removeAtIndex(3);
    }

    @Test
    public void removeAtIndex()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        linkedList.addLast('A');
        linkedList.addLast('B');
        linkedList.addLast('C');

        assertThat(linkedList.removeAtIndex(0), is('A'));
        assertThat(linkedList.removeAtIndex(1), is('C'));
        assertThat(linkedList.removeAtIndex(0), is('B'));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeNoSuchElem()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        linkedList.addLast('A');

        linkedList.remove('B');
    }

    @Test
    public void remove()
    {
        final LinkedCharList linkedList = new LinkedCharList();

        linkedList.addLast('A');
        linkedList.addLast('B');
        linkedList.addLast('C');

        assertThat(linkedList.remove('B'), is(1));
        assertThat(linkedList.size(), is(2));

        linkedList.addLast('D');

        assertThat(linkedList.size(), is(3));
        assertThat(linkedList.remove('D'), is(2));
        assertThat(linkedList.size(), is(2));

        assertThat(linkedList.remove('A'), is(0));
        assertThat(linkedList.remove('C'), is(0));

        assertThat(linkedList.isEmpty(), is(true));
        assertThat(linkedList.size(), is(0));
    }
}