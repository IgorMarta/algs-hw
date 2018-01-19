import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SAPTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArg()
    {
        new SAP(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthIllegalIntArg()
    {
        new SAP(buildGraph()).length(3, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthIllegalIntArg2()
    {
        new SAP(buildGraph()).length(30, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthIllegalObjArg()
    {
        new SAP(buildGraph()).length(emptyList(), null);
    }

    @Test
    public void length()
    {
        final SAP sap = new SAP(buildGraph());

        assertThat(sap.length(3, 11), is(4));
        assertThat(sap.length(9, 12), is(3));
        assertThat(sap.length(7, 2), is(4));
        assertThat(sap.length(1, 6), is(-1));

        assertThat(sap.length(asList(3, 9), asList(11, 12)), is(3));
        assertThat(sap.length(asList(7, 1), asList(2, 5)), is(1));
        assertThat(sap.length(asList(7, 10, 2), asList(9, 8, 4)), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestorIllegalIntArg()
    {
        new SAP(buildGraph()).ancestor(3, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestorIllegalIntArg2()
    {
        new SAP(buildGraph()).ancestor(3, 13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ancestorIllegalObjArg()
    {
        new SAP(buildGraph()).ancestor(emptyList(), null);
    }

    @Test
    public void ancestor()
    {
        final SAP sap = new SAP(buildGraph());

        assertThat(sap.ancestor(3, 11), is(1));
        assertThat(sap.ancestor(9, 12), is(5));
        assertThat(sap.ancestor(7, 2), is(0));
        assertThat(sap.ancestor(1, 6), is(-1));

        assertThat(sap.ancestor(asList(3, 9), asList(11, 12)), is(5));
        assertThat(sap.ancestor(asList(7, 1), asList(2, 5)), is(1));
        assertThat(sap.ancestor(asList(7, 10, 2), asList(9, 8, 4)), is(3));
    }

    private Digraph buildGraph()
    {
        return new Digraph(new In("wordnet/digraph1.txt"));
    }
}