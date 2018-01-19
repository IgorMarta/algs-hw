import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class MoveToFrontTest extends BinaryIOTest
{
    @Test
    public void encode()
    {
        final String input = "ABRACADABRA!";
        final byte[] expected = { 0x41, 0x42, 0x52, 0x02, 0x44, 0x01, 0x45, 0x01, 0x04, 0x04, 0x02, 0x26 };

        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        MoveToFront.encode();

        assertThat(byteArrayOutput.toByteArray(), equalTo(expected));
    }

    @Test
    public void decode()
    {
        final byte[] input = { 0x41, 0x42, 0x52, 0x02, 0x44, 0x01, 0x45, 0x01, 0x04, 0x04, 0x02, 0x26 };
        final String expected = "ABRACADABRA!";

        final InputStream inputStream = new ByteArrayInputStream(input);
        System.setIn(inputStream);

        MoveToFront.decode();

        assertThat(byteArrayOutput.toString(), equalTo(expected));
    }
}