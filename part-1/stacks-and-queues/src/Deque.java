import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private Node<Item> head;
    private Node<Item> tail;
    private int nrOfNodes = 0;

    public Deque()
    {
    }

    public boolean isEmpty()
    {
        return head == null;
    }

    public int size()
    {
        return nrOfNodes;
    }

    public void addFirst(final Item item)
    {
        validateItem(item);
        final Node<Item> oldHead = head;
        head = new Node<>(item);

        if (oldHead == null)
        {
            tail = head;
        }
        else
        {
            head.next = oldHead;
            oldHead.prev = head;
        }
        nrOfNodes++;
    }

    public void addLast(final Item item)
    {
        validateItem(item);
        final Node<Item> oldTail = tail;
        tail = new Node<>(item);

        if (isEmpty())
        {
            head = tail;
        }
        else
        {
            oldTail.next = tail;
            tail.prev = oldTail;
        }
        nrOfNodes++;
    }

    public Item removeFirst()
    {
        validateSize();
        final Node<Item> node = head;
        head = head.next;

        if (isEmpty())
        {
            tail = null;
        }
        else
        {
            head.prev = null;
        }
        nrOfNodes--;

        return node.item;
    }

    public Item removeLast()
    {
        validateSize();
        final Node<Item> node = tail;
        tail = tail.prev;

        if (tail == null)
        {
            head = null;
        }
        else
        {
            tail.next = null;
        }
        nrOfNodes--;

        return node.item;
    }

    private void validateSize()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
    }

    private void validateItem(final Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class Node<T>
    {
        private final T item;
        private Node<T> prev;
        private Node<T> next;

        Node(final T item)
        {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node<Item> current = head;

        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public Item next()
        {
            if (hasNext())
            {
                final Item next = current.item;
                current = current.next;
                return next;
            }
            else
            {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
