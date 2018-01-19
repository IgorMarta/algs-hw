import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BaseballEliminationTest
{
    @Test
    public void numberOfTeams()
    {
        assertThat(teams1().numberOfTeams(), is(1));
        assertThat(teams4().numberOfTeams(), is(4));
        assertThat(teams5().numberOfTeams(), is(5));
        assertThat(teams10().numberOfTeams(), is(10));
    }

    @Test
    public void teams()
    {
        assertThat(teams1().teams(), hasItems("Turing"));
        assertThat(teams4().teams(), hasItems("Atlanta", "Philadelphia", "New_York", "Montreal"));
        assertThat(teams5().teams(), hasItems("New_York", "Baltimore", "Boston", "Toronto", "Detroit"));
        assertThat(teams10().teams(), hasItems("Atlanta", "Boston", "Chicago", "Cleveland", "Dallas",
            "Denver", "Detroit", "Golden_State", "Houston", "Indiana"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void winsNullArg()
    {
        teams4().wins(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void winsInvalidTeam()
    {
        teams4().wins("Chisinau");
    }

    @Test
    public void wins()
    {
        final BaseballElimination teams5 = teams5();

        assertThat(teams5.wins("Baltimore"), is(71));
        assertThat(teams5.wins("New_York"), is(75));
        assertThat(teams5.wins("Boston"), is(69));
        assertThat(teams5.wins("Toronto"), is(63));
        assertThat(teams5.wins("Detroit"), is(49));
    }

    @Test(expected = IllegalArgumentException.class)
    public void lossesNullArg()
    {
        teams4().losses(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lossesInvalidArg()
    {
        teams4().losses("Bucharest");
    }

    @Test
    public void losses()
    {
        final BaseballElimination teams5 = teams5();

        assertThat(teams5.losses("Baltimore"), is(63));
        assertThat(teams5.losses("New_York"), is(59));
        assertThat(teams5.losses("Boston"), is(66));
        assertThat(teams5.losses("Toronto"), is(72));
        assertThat(teams5.losses("Detroit"), is(86));
    }

    @Test(expected = IllegalArgumentException.class)
    public void remainingNullArg()
    {
        teams4().remaining(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void remainingInvalidTeam()
    {
        teams4().remaining("Athens");
    }

    @Test
    public void remaining()
    {
        final BaseballElimination teams5 = teams5();

        assertThat(teams5.remaining("Baltimore"), is(28));
        assertThat(teams5.remaining("New_York"), is(28));
        assertThat(teams5.remaining("Boston"), is(27));
        assertThat(teams5.remaining("Toronto"), is(27));
        assertThat(teams5.remaining("Detroit"), is(27));
    }

    @Test(expected = IllegalArgumentException.class)
    public void againstNullArg()
    {
        teams4().against(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void againstInvalidTeams()
    {
        teams4().against("Chisinau", "Bucharest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void againstInvalidTeam()
    {
        teams4().against("Baltimore", "Bucharest");
    }

    @Test
    public void against()
    {
        final BaseballElimination teams5 = teams5();

        assertThat(teams5.against("Baltimore", "New_York"), is(3));
        assertThat(teams5.against("New_York", "Boston"), is(8));
        assertThat(teams5.against("Boston", "Toronto"), is(0));
        assertThat(teams5.against("Toronto", "Detroit"), is(3));
        assertThat(teams5.against("Detroit", "Baltimore"), is(7));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEliminatedNullArg()
    {
        teams4().isEliminated(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEliminatedInvalidTeam()
    {
        teams4().isEliminated("Chisinau");
    }

    @Test
    public void isEliminated()
    {
        final BaseballElimination teams4 = teams4(), teams5 = teams5();

        assertThat(teams4.isEliminated("Montreal"), is(true));
        assertThat(teams4.isEliminated("New_York"), is(false));
        assertThat(teams4.isEliminated("Atlanta"), is(false));
        assertThat(teams4.isEliminated("Philadelphia"), is(true));

        assertThat(teams5.isEliminated("Detroit"), is(true));
        assertThat(teams5.isEliminated("Toronto"), is(false));
        assertThat(teams5.isEliminated("Boston"), is(false));
        assertThat(teams5.isEliminated("New_York"), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void certificateOfEliminationNullArg()
    {
        teams4().certificateOfElimination(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void certificateOfEliminationInvalidTeam()
    {
        teams4().certificateOfElimination("Chisinau");
    }

    @Test
    public void certificateOfElimination()
    {
        final BaseballElimination teams4 = teams4(), teams5 = teams5();

        assertThat(teams4.certificateOfElimination("Montreal"), hasItems("Atlanta"));
        assertThat(teams4.certificateOfElimination("New_York"), nullValue());
        assertThat(teams4.certificateOfElimination("Atlanta"), nullValue());
        assertThat(teams4.certificateOfElimination("Philadelphia"), hasItems("Atlanta", "New_York"));

        assertThat(teams5.certificateOfElimination("Detroit"), hasItems("New_York", "Baltimore", "Boston", "Toronto"));
        assertThat(teams5.certificateOfElimination("Toronto"), nullValue());
        assertThat(teams5.certificateOfElimination("Boston"), nullValue());
        assertThat(teams5.certificateOfElimination("New_York"), nullValue());
    }

    private BaseballElimination teams1()
    {
        return new BaseballElimination("baseball/teams1.txt");
    }

    private BaseballElimination teams4()
    {
        return new BaseballElimination("baseball/teams4.txt");
    }

    private BaseballElimination teams5()
    {
        return new BaseballElimination("baseball/teams5.txt");
    }

    private BaseballElimination teams10()
    {
        return new BaseballElimination("baseball/teams10.txt");
    }
}