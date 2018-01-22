import java.util.Arrays;

public final class DivisionRow
{
    private final int id;
    private final String team;
    private final int wins;
    private final int losses;
    private final int remaining;
    private final int[] remainingAgainst;

    DivisionRow(final int id, final String team, final int wins, final int losses,
                final int remaining, final int[] remainingAgainst)
    {
        this.id = id;
        this.team = team;
        this.wins = wins;
        this.losses = losses;
        this.remaining = remaining;
        this.remainingAgainst = remainingAgainst;
    }

    public int getId()
    {
        return id;
    }

    public String getTeam()
    {
        return team;
    }

    public int getWins()
    {
        return wins;
    }

    public int getLosses()
    {
        return losses;
    }

    public int getRemaining()
    {
        return remaining;
    }

    public int[] getRemainingAgainst()
    {
        return Arrays.copyOf(remainingAgainst, remainingAgainst.length);
    }
}
