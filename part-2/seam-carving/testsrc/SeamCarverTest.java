import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class SeamCarverTest
{
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionForNullArg()
    {
        new SeamCarver(null);
    }

    @Test
    public void picture()
    {
        final Picture picture = new Picture(10, 10);
        final SeamCarver seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.picture(), equalTo(picture));
    }

    @Test
    public void width()
    {
        final int width = 20;
        final Picture picture = new Picture(width, 10);
        final SeamCarver seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.width(), is(width));
    }

    @Test
    public void height()
    {
        final int height = 20;
        final Picture picture = new Picture(10, height);
        final SeamCarver seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.height(), is(height));
    }

    @Test(expected = IllegalArgumentException.class)
    public void energyIllegalArgs()
    {
        final Picture picture = new Picture(20, 10);
        new SeamCarver(picture).energy(20, 9);
    }

    @Test
    public void energy()
    {
        final SeamCarver seamCarver = new SeamCarver(getPicture());

        assertThat(seamCarver.energy(1, 1), is(Math.sqrt(52225)));
        assertThat(seamCarver.energy(1, 2), is(Math.sqrt(52024)));
        assertThat(seamCarver.energy(2,2), is(1000.0));
        assertThat(seamCarver.energy(1, 0), is(1000.0));
    }

    @Test
    public void findHorizontalSeam()
    {
        final SeamCarver seamCarver = new SeamCarver(getPicture());

        assertThat(Arrays.stream(seamCarver.findHorizontalSeam())
            .boxed()
            .toArray(), arrayContaining(1, 2, 1));
    }

    @Test
    public void findVerticalSeam()
    {
        final SeamCarver seamCarver = new SeamCarver(getPicture());

        assertThat(Arrays.stream(seamCarver.findVerticalSeam())
            .boxed()
            .toArray(), arrayContaining(0, 1, 1, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeamNullArg()
    {
        new SeamCarver(getPicture()).removeHorizontalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeamMinHeight()
    {
        new SeamCarver(new Picture(10, 1)).removeHorizontalSeam(new int[10]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeamWrongLength()
    {
        new SeamCarver(new Picture(10, 5)).removeHorizontalSeam(new int[9]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeamOutOfRange()
    {
        final int[] seam = { 1, 2, 1, 2, 3 };
        new SeamCarver(new Picture(5, 3)).removeHorizontalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeamInvalid()
    {
        final int[] seam = { 1, 2, 0, 2, 1 };
        new SeamCarver(new Picture(5, 3)).removeHorizontalSeam(seam);
    }

    @Test
    public void removeHorizontalSeam()
    {
        final int[] seam = { 1, 2, 1 };
        final Picture picture = getPicture();
        final SeamCarver seamCarver = new SeamCarver(picture);

        seamCarver.removeHorizontalSeam(seam);

        assertThat(seamCarver.height(), is(3));
        assertThat(seamCarver.picture(), not(equalTo(picture)));
        assertThat(getColors(seamCarver.picture()), not(arrayContaining(getHorizontalSeamColors(seam, picture))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeamNullArg()
    {
        new SeamCarver(getPicture()).removeVerticalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeamMinWidth()
    {
        new SeamCarver(new Picture(1, 10)).removeVerticalSeam(new int[10]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeamWrongLength()
    {
        new SeamCarver(new Picture(10, 5)).removeVerticalSeam(new int[4]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeamOutOfRange()
    {
        final int[] seam = { 1, 2, 1, 2, 3 };
        new SeamCarver(new Picture(3, 5)).removeVerticalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeamInvalid()
    {
        final int[] seam = { 1, 2, 0, 2, 1 };
        new SeamCarver(new Picture(3, 5)).removeVerticalSeam(seam);
    }

    @Test
    public void removeVerticalSeam()
    {
        final int[] seam = { 1, 2, 2, 1 };
        final Picture picture = getPicture();
        final SeamCarver seamCarver = new SeamCarver(picture);

        seamCarver.removeVerticalSeam(seam);

        assertThat(seamCarver.width(), is(2));
        assertThat(seamCarver.picture(), not(equalTo(picture)));
        assertThat(getColors(seamCarver.picture()), not(arrayContaining(getVerticalSeamColors(seam, picture))));
    }

    private Picture getPicture()
    {
        final int width = 3, height = 4;
        final Color[][] colors = {
            { rgb(255, 101, 51), rgb(255, 101, 153), rgb(255, 101, 255) },
            { rgb(255, 153, 51), rgb(255, 153, 153), rgb(255, 153, 255) },
            { rgb(255, 203, 51), rgb(255, 204, 153), rgb(255, 205, 255) },
            { rgb(255, 255, 51), rgb(255, 255, 153), rgb(255, 255, 255) }
        };

        final Picture picture = new Picture(width, height);

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                picture.set(j, i, colors[i][j]);
            }
        }
        return picture;
    }

    private Color rgb(final int r, final int g, final int b)
    {
        return new Color(r, g, b);
    }

    private Color[] getColors(final Picture picture)
    {
        return IntStream.range(0, picture.height())
            .boxed()
            .flatMap(i -> IntStream.range(0, picture.width()).mapToObj(j -> picture.get(j, i)))
            .toArray(Color[]::new);
    }

    private Color[] getHorizontalSeamColors(final int[] seam, final Picture picture)
    {
        return IntStream.range(0, picture.width())
            .mapToObj(i -> picture.get(i, seam[i]))
            .toArray(Color[]::new);
    }

    private Color[] getVerticalSeamColors(final int[] seam, final Picture picture)
    {
        return IntStream.range(0, picture.height())
            .mapToObj(i -> picture.get(seam[i], i))
            .toArray(Color[]::new);
    }
}