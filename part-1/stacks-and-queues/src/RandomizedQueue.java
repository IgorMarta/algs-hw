import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] items;
    private int nrOfItems = 0;

    public RandomizedQueue()
    {
        items = newItemArray(2);
    }

    public boolean isEmpty()
    {
        return nrOfItems == 0;
    }

    public int size()
    {
        return nrOfItems;
    }

    public void enqueue(final Item item)
    {
        validateItem(item);

        if (nrOfItems == items.length)
        {
            resize(2 * items.length);
        }

        items[nrOfItems++] = item;
    }


    public Item dequeue()
    {
        validateSize();
        final int randPos = StdRandom.uniform(0, nrOfItems);
        final Item item = items[randPos];

        items[randPos] = items[nrOfItems - 1];
        items[nrOfItems - 1] = null;
        nrOfItems--;

        if (nrOfItems > 0 && nrOfItems == items.length / 4)
        {
            resize(items.length / 2);
        }

        return item;
    }

    public Item sample()
    {
        validateSize();
        return items[StdRandom.uniform(0, nrOfItems)];
    }

    private void resize(final int newSize)
    {
        final Item[] copy = newItemArray(newSize);
        copyItems(copy);
        items = copy;
    }

    private Item[] newItemArray(final int size)
    {
        return (Item[]) new Object[size];
    }

    private void copyItems(final Item[] copy)
    {
        for (int i = 0; i < nrOfItems; i++)
        {
            copy[i] = items[i];
        }
    }

    private void validateItem(final Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
    }

    private void validateSize()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private final Item[] shuffledCopy;
        private int current = 0;

        RandomizedQueueIterator()
        {
            shuffledCopy = newItemArray(nrOfItems);
            copyItems(shuffledCopy);
            StdRandom.shuffle(shuffledCopy);
        }

        @Override
        public boolean hasNext()
        {
            return current < shuffledCopy.length;
        }

        @Override
        public Item next()
        {
            if (hasNext())
            {
                return shuffledCopy[current++];
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
