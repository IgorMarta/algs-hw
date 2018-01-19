import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BurrowsWheelerTest extends BinaryIOTest
{
    @Test
    public void transform()
    {
        final String input = "ABRACADABRA!";
        final byte[] expected = { // 3ARD!RCAAAABB
            0x00, 0x00, 0x00, 0x03, 0x41, 0x52, 0x44, 0x21,
            0x52, 0x43, 0x41, 0x41, 0x41, 0x41, 0x42, 0x42 };
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        BurrowsWheeler.transform();

        assertThat(byteArrayOutput.toByteArray(), equalTo(expected));
    }

    @Test
    public void inverseTransform()
    {
        final byte[] inputData = { // 3ARD!RCAAAABB
            0x00, 0x00, 0x00, 0x03, 0x41, 0x52, 0x44, 0x21,
            0x52, 0x43, 0x41, 0x41, 0x41, 0x41, 0x42, 0x42 };
        final String expected = "ABRACADABRA!";
        final InputStream inputStream = new ByteArrayInputStream(inputData);
        System.setIn(inputStream);

        BurrowsWheeler.inverseTransform();

        assertThat(byteArrayOutput.toString(), equalTo(expected));
    }

    @Test
    public void inverseTransformStars()
    {
        final In in = new In("stars.txt.bwt");
        final byte[] inputData = in.readAll().getBytes();
        final String expected = "*************";
        final InputStream inputStream = new ByteArrayInputStream(inputData);
        System.setIn(inputStream);

        BurrowsWheeler.inverseTransform();

        assertThat(byteArrayOutput.toString(), equalTo(expected));
    }
}