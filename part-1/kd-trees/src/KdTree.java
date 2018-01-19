import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree
{
    private Node root;
    private int size = 0;

    public boolean isEmpty()
    {
        return root == null;
    }

    public int size()
    {
        return size;
    }

    public void insert(final Point2D point)
    {
        validateArg(point);

        if (!contains(point))
        {
            root = insert(root, point, 0);
            size++;
        }
    }

    private Node insert(final Node node, final Point2D point, final int level)
    {
        if (node == null)
        {
            return new Node(point);
        }

        if (comparePoints(point, node.point, level) < 0)
        {
            node.left = insert(node.left, point, level + 1);
        }
        else
        {
            node.right = insert(node.right, point, level + 1);
        }

        return node;
    }

    public boolean contains(final Point2D point)
    {
        validateArg(point);
        return contains(root, point, 0);
    }

    private boolean contains(final Node node, final Point2D point, final int level)
    {
        if (node == null)
        {
            return false;
        }

        if (point.equals(node.point))
        {
            return true;
        }

        if (comparePoints(point, node.point, level) < 0)
        {
            return contains(node.left, point, level + 1);
        }
        else
        {
            return contains(node.right, point, level + 1);
        }
    }

    public void draw()
    {
        draw(root, 0, 0, 1, 1, 0);
    }

    private void draw(final Node node, final double xmin, final double ymin, final double xmax,
                      final double ymax, final int level)
    {
        if (node == null)
        {
            return;
        }

        StdDraw.setPenRadius();
        if (isEven(level))
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), ymin, node.point.x(), ymax);
            draw(node.left, xmin, ymin, node.point.x(), ymax, level + 1);
            draw(node.right, node.point.x(), ymin, xmax, ymax, level + 1);
        }
        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, node.point.y(), xmax, node.point.y());
            draw(node.left, xmin, ymin, xmax, node.point.y(), level + 1);
            draw(node.right, xmin, node.point.y(), xmax, ymax, level + 1);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
    }

    public Iterable<Point2D> range(final RectHV rect)
    {
        validateArg(rect);
        return range(root, new RectHV(0, 0, 1, 1), new ArrayList<>(), rect, 0);
    }

    private Iterable<Point2D> range(final Node node, final RectHV nodeRect, final List<Point2D> range,
                                    final RectHV rect, final int level)
    {
        if (node == null || !rect.intersects(nodeRect))
        {
            return range;
        }

        if (rect.contains(node.point))
        {
            range.add(node.point);
        }

        range(node.left, nextNodeRect(nodeRect, node.point, level, TreePath.LEFT), range, rect, level + 1);
        range(node.right, nextNodeRect(nodeRect, node.point, level, TreePath.RIGHT), range, rect, level + 1);

        return range;
    }

    public Point2D nearest(final Point2D p)
    {
        validateArg(p);
        return isEmpty() ? null : nearest(root, new RectHV(0, 0, 1, 1), root.point, p, 0);
    }

    private Point2D nearest(final Node node, final RectHV nodeRect, final Point2D nearest,
                            final Point2D point, final int level)
    {
        if (node == null || Double.compare(nodeRect.distanceSquaredTo(point), nearest.distanceSquaredTo(point)) >= 0)
        {
            return nearest;
        }

        Point2D nearestPoint = nearest;
        if (Double.compare(node.point.distanceSquaredTo(point), nearest.distanceSquaredTo(point)) < 0)
        {
            nearestPoint = node.point;
        }

        if (comparePoints(point, node.point, level) < 0)
        {
            nearestPoint = nearest(node.left, nextNodeRect(nodeRect, node.point, level, TreePath.LEFT),
                nearestPoint, point, level + 1);
            nearestPoint = nearest(node.right, nextNodeRect(nodeRect, node.point, level, TreePath.RIGHT),
                nearestPoint, point, level + 1);
        }
        else
        {
            nearestPoint = nearest(node.right, nextNodeRect(nodeRect, node.point, level, TreePath.RIGHT),
                nearestPoint, point, level + 1);
            nearestPoint = nearest(node.left, nextNodeRect(nodeRect, node.point, level, TreePath.LEFT),
                nearestPoint, point, level + 1);
        }

        return nearestPoint;
    }

    private RectHV nextNodeRect(final RectHV prevRect, final Point2D nodePoint, final int level, final TreePath path)
    {
        if (TreePath.LEFT == path)
        {
            return isEven(level)
                ? new RectHV(prevRect.xmin(), prevRect.ymin(), nodePoint.x(), prevRect.ymax())
                : new RectHV(prevRect.xmin(), prevRect.ymin(), prevRect.xmax(), nodePoint.y());
        }
        else
        {
            return isEven(level)
                ? new RectHV(nodePoint.x(), prevRect.ymin(), prevRect.xmax(), prevRect.ymax())
                : new RectHV(prevRect.xmin(), nodePoint.y(), prevRect.xmax(), prevRect.ymax());
        }
    }

    private boolean isEven(final int level)
    {
        return level % 2 == 0;
    }

    private int comparePoints(final Point2D point, final Point2D nodePoint, final int level)
    {
        return isEven(level)
            ? Double.compare(point.x(), nodePoint.x())
            : Double.compare(point.y(), nodePoint.y());
    }

    private void validateArg(final Object object)
    {
        if (object == null)
        {
            throw new IllegalArgumentException();
        }
    }

    private static class Node
    {
        private final Point2D point;
        private Node left;
        private Node right;

        Node(final Point2D point)
        {
            this.point = point;
        }
    }

    private enum TreePath
    {
        LEFT, RIGHT
    }
}
