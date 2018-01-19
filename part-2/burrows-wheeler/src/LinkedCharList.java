import java.util.NoSuchElementException;

public class LinkedCharList
{
    private Node head;
    private Node tail;
    private int nrOfNodes = 0;

    public boolean isEmpty()
    {
        return head == null;
    }

    public int size()
    {
        return nrOfNodes;
    }

    public void addFirst(final char item)
    {
        final Node oldHead = head;
        head = new Node(item);

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

    public void addLast(final char item)
    {
        final Node oldTail = tail;
        tail = new Node(item);

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

    public char removeAtIndex(final int index)
    {
        validateSize();
        validateIndex(index);

        final Node node = get(index);

        if (node == head)
        {
            removeFirst();
            return node.item;
        }

        if (node == tail)
        {
            removeLast();
            return node.item;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
        nrOfNodes--;

        return node.item;
    }

    public int remove(final char item)
    {
        validateSize();

        Node node = head;
        int index = 0;

        while (node != null && node.item != item)
        {
            node = node.next;
            index++;
        }

        if (node == null)
        {
            throw new NoSuchElementException();
        }

        if (node == head)
        {
            removeFirst();
            return index;
        }

        if (node == tail)
        {
            removeLast();
            return index;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
        nrOfNodes--;

        return index;
    }

    private void removeFirst()
    {
        validateSize();
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
    }

    private void removeLast()
    {
        validateSize();
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
    }

    private Node get(final int index)
    {
        Node node = head;

        for (int pos = 0; node != null && index != pos; pos++)
        {
            node = node.next;
        }

        return node;
    }

    private void validateSize()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
    }

    private void validateIndex(int index)
    {
        if (index < 0 || index >= size())
        {
            throw new NoSuchElementException();
        }
    }

    private class Node
    {
        private final char item;
        private Node next, prev;

        Node(final char item)
        {
            this.item = item;
        }
    }
}
