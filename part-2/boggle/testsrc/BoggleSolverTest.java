import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BoggleSolverTest
{
    @Test
    public void getAllValidWords()
    {
        final String[] dictionary = {"THEN"};
        final BoggleBoard diagonalBoard = new BoggleBoard("board-diagonal.txt"),
            board4 = new BoggleBoard("board4x4.txt"),
            boardQ = new BoggleBoard("board-q.txt");
        final BoggleSolver diagonal = new BoggleSolver(dictionary),
            algs4 = new BoggleSolver(readDictionary());

        assertThat(diagonal.getAllValidWords(diagonalBoard), contains("THEN"));

        assertThat(algs4.getAllValidWords(board4),
            contains("PAT", "TAT", "PATE", "ONE", "YET", "EYE", "UNITE", "ENDS", "UNITED", "AID", "TYPE", "DIE", "SITE",
                "UNIT", "USE", "TINY", "ONES", "PAID", "SINE", "SEND", "TIE", "SIDE", "TIED", "TIN", "SIN", "END",
                "PAINS", "YOU", "SIT"));

        assertThat(algs4.getAllValidWords(boardQ),
            contains("RES", "SER", "TIES", "TAT", "ONE", "EQUATIONS", "ITS", "STATE", "QUITE", "QUESTION", "TENS",
                "QUERIES", "LETS", "EQUATION", "REQUIRE", "SITE", "SINE", "QUESTIONS", "TIE", "REST", "SITS", "REQUEST",
                "TIN", "SIN", "LET", "NET", "TEN", "SIT", "TRIES"));
    }

    @Test
    public void scoreOf()
    {
        final String[] dictionary = {"AB", "ABC", "CDETGRE", "CDEFG", "QWERTY", "ASDFGHJKL", "ZX", "ABCD"};
        final BoggleSolver boggleSolver = new BoggleSolver(dictionary);

        assertThat(boggleSolver.scoreOf("AB"), is(0));
        assertThat(boggleSolver.scoreOf("AAAAAAA"), is(0));
        assertThat(boggleSolver.scoreOf("ABC"), is(1));
        assertThat(boggleSolver.scoreOf("ABCD"), is(1));
        assertThat(boggleSolver.scoreOf("CDEFG"), is(2));
        assertThat(boggleSolver.scoreOf("QWERTY"), is(3));
        assertThat(boggleSolver.scoreOf("CDETGRE"), is(5));
        assertThat(boggleSolver.scoreOf("ASDFGHJKL"), is(11));
    }

    private String[] readDictionary()
    {
        return new In("dictionary-algs4.txt").readAllStrings();
    }
}