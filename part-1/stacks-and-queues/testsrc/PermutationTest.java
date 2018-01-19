import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;

public class PermutationTest
{
    private ByteArrayOutputStream byteArrayOutput;

    @Before
    public void setup()
    {
        byteArrayOutput = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(byteArrayOutput);
        System.setOut(printStream);
    }

    @Test
    public void testOutput()
    {
        final int size = 5;
        final String input = "A B C D E F G H I";
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Permutation.main(new String[]{String.valueOf(size)});

        final List<String> result = Arrays.stream(byteArrayOutput.toString()
            .split(System.getProperty("line.separator")))
            .collect(Collectors.toList());

        final String[] inputData = input.split("\\s");

        assertThat(result, allOf(everyItem(isIn(inputData)), iterableWithSize(size)));
    }

    @After
    public void clean()
    {
        final PrintStream originalOut = System.out;
        final InputStream originalIn = System.in;
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}