import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public abstract class BinaryIOTest
{
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final InputStream ORIGINAL_IN = System.in;

    protected ByteArrayOutputStream byteArrayOutput;

    @Before
    public void setup()
    {
        byteArrayOutput = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(byteArrayOutput);
        System.setOut(printStream);
    }

    @After
    public void clean()
    {
        System.setOut(ORIGINAL_OUT);
        System.setIn(ORIGINAL_IN);
    }
}
