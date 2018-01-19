public class StringTrieST
{
    private static final int DATASET_SIZE = 26;

    private Node root;

    static class Node
    {
        private String val;
        private Node[] next = new Node[DATASET_SIZE];
    }

    public String get(final String key)
    {
        if (key == null)
        {
            throw new IllegalArgumentException();
        }

        final Node node = get(root, key, 0);

        if (node == null)
        {
            return null;
        }

        return node.val;
    }

    private Node get(final Node node, final String key, final int level)
    {
        Node nodeIterator = node;

        for (int index = level; nodeIterator != null && index < key.length(); index++)
        {
            nodeIterator = nodeIterator.next[val(key.charAt(index))];
        }

        return nodeIterator;
    }

    public boolean contains(final String key)
    {
        return get(key) != null;
    }

    public void put(final String key, final String val)
    {
        if (key == null)
        {
            throw new IllegalArgumentException();
        }

        if (val != null)
        {
            root = put(root, key, val, 0);
        }
    }

    private Node put(final Node node, final String key, final String val, final int level)
    {
        final Node newNode = node == null ? new Node() : node;

        if (level == key.length())
        {
            newNode.val = val;
            return newNode;
        }

        final int index = val(key.charAt(level));
        newNode.next[index] = put(newNode.next[index], key, val, level + 1);

        return newNode;
    }

    public Node nodeWithPrefix(final String prefix, final Node customRoot, final int levelGap)
    {
        return customRoot == null
            ? get(root, prefix, 0)
            : get(customRoot, prefix, prefix.length() - levelGap);
    }

    private int val(final char c)
    {
        return Character.getNumericValue(c) - 10;
    }
}
