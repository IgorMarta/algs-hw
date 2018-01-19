import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BaseballElimination
{
    private static final int SOURCE_VERTEX = 0;
    private final DivisionRow[] divisionRows;

    public BaseballElimination(final String filename)
    {
        final In divisionIn = new In(filename);

        if (divisionIn.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        else
        {
            divisionRows = readDivisionData(divisionIn, divisionIn.readInt());
        }
    }

    public int numberOfTeams()
    {
        return divisionRows.length;
    }

    public Iterable<String> teams()
    {
        return Arrays.stream(divisionRows)
            .map(DivisionRow::getTeam)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public int wins(final String team)
    {
        return getTeam(team).getWins();
    }

    public int losses(final String team)
    {
        return getTeam(team).getLosses();
    }

    public int remaining(final String team)
    {
        return getTeam(team).getRemaining();
    }

    public int against(final String team1, final String team2)
    {
        return getTeam(team1).getRemainingAgainst()[getTeam(team2).getId()];
    }

    public boolean isEliminated(final String team)
    {
        final DivisionRow divisionTeam = getTeam(team);
        return trivialElimination(divisionTeam) || nonTrivialElimination(divisionTeam);
    }

    public Iterable<String> certificateOfElimination(final String team)
    {
        final DivisionRow divisionTeam = getTeam(team);

        return Optional.of(getTrivialCertificate(divisionTeam))
            .filter(certificate -> !certificate.isEmpty())
            .orElseGet(() -> getNonTrivialCertificate(divisionTeam));
    }

    private boolean trivialElimination(final DivisionRow divisionTeam)
    {
        return Arrays.stream(divisionRows)
            .mapToInt(DivisionRow::getWins)
            .anyMatch(wins -> isTriviallyEliminated(divisionTeam, wins));
    }

    private boolean nonTrivialElimination(final DivisionRow divisionTeam)
    {
        final FlowNetwork flowNetwork = initFlowNetworkFor(divisionTeam);

        computeMaxFlow(divisionTeam, flowNetwork);

        return isNonTriviallyEliminated(flowNetwork);
    }

    private FlowNetwork initFlowNetworkFor(final DivisionRow divisionTeam)
    {
        final FlowNetwork flowNetwork = new FlowNetwork(getNumberOfVertices());

        addGameEdges(divisionTeam, flowNetwork);
        addSinkEdges(divisionTeam, flowNetwork);

        return flowNetwork;
    }

    private FordFulkerson computeMaxFlow(final DivisionRow divisionTeam, final FlowNetwork flowNetwork)
    {
        return new FordFulkerson(flowNetwork, SOURCE_VERTEX, divisionTeam.getId() + 1);
    }

    private void addGameEdges(final DivisionRow divisionTeam, final FlowNetwork flowNetwork)
    {
        int gameVertex = flowNetwork.V() - nChoose2(numberOfTeams() - 1);

        for (int i = 0; i < numberOfTeams(); i++)
        {
            if (notTargetTeam(divisionTeam, i))
            {
                for (int j = i + 1; j < numberOfTeams(); j++)
                {
                    if (notTargetTeam(divisionTeam, j))
                    {
                        addEdge(flowNetwork, SOURCE_VERTEX, gameVertex, divisionRows[i].getRemainingAgainst()[j]);
                        addEdge(flowNetwork, gameVertex, i + 1, Double.POSITIVE_INFINITY);
                        addEdge(flowNetwork, gameVertex, j + 1, Double.POSITIVE_INFINITY);
                        gameVertex++;
                    }
                }
            }
        }
    }

    private void addSinkEdges(final DivisionRow team, final FlowNetwork flowNetwork)
    {
        Arrays.stream(divisionRows)
            .filter(row -> notTargetTeam(team, row.getId()))
            .forEach(row -> addEdge(flowNetwork, row.getId() + 1, team.getId() + 1,
                team.getWins() + team.getRemaining() - row.getWins()));
    }

    private void addEdge(final FlowNetwork flowNetwork, final int from, final int to, final double capacity)
    {
        flowNetwork.addEdge(new FlowEdge(from, to, capacity));
    }

    private boolean notTargetTeam(final DivisionRow divisionTeam, final int row)
    {
        return row != divisionTeam.getId();
    }

    private int getNumberOfVertices()
    {
        final int n = numberOfTeams() - 1;
        return 2 + n + nChoose2(n);
    }

    private int nChoose2(final int n)
    {
        return (n - 1) * n / 2;
    }

    private List<String> getTrivialCertificate(final DivisionRow divisionTeam)
    {
        return Arrays.stream(divisionRows)
            .filter(row -> isTriviallyEliminated(divisionTeam, row.getWins()))
            .map(DivisionRow::getTeam)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private boolean isTriviallyEliminated(final DivisionRow divisionTeam, final int wins)
    {
        return divisionTeam.getWins() + divisionTeam.getRemaining() < wins;
    }

    private boolean isNonTriviallyEliminated(final FlowNetwork flowNetwork)
    {
        for (final FlowEdge edge : flowNetwork.adj(SOURCE_VERTEX))
        {
            if (edge.flow() < edge.capacity())
            {
                return true;
            }
        }
        return false;
    }

    private List<String> getNonTrivialCertificate(final DivisionRow divisionTeam)
    {
        return Optional.of(getInCutTeams(computeMaxFlow(divisionTeam, initFlowNetworkFor(divisionTeam))))
            .filter(certificate -> !certificate.isEmpty())
            .orElse(null);
    }

    private List<String> getInCutTeams(final FordFulkerson fordFulkerson)
    {
        return Arrays.stream(divisionRows)
            .filter(row -> fordFulkerson.inCut(row.getId() + 1))
            .map(DivisionRow::getTeam)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private DivisionRow getTeam(final String team)
    {
        return Arrays.stream(divisionRows)
            .filter(row -> Objects.equals(team, row.getTeam()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    private DivisionRow[] readDivisionData(final In divisionIn, final int nrOfTeams)
    {
        final DivisionRow[] rows = new DivisionRow[nrOfTeams];

        for (int index = 0; index < nrOfTeams && divisionIn.hasNextLine(); index++)
        {
            rows[index] = new DivisionRow(index,
                divisionIn.readString(),
                divisionIn.readInt(),
                divisionIn.readInt(),
                divisionIn.readInt(),
                readRemainingAgainst(divisionIn, nrOfTeams));
        }

        return rows;
    }

    private int[] readRemainingAgainst(final In divisionIn, final int nrOfTeams)
    {
        return Arrays.stream(new int[nrOfTeams])
            .map(i -> divisionIn.readInt())
            .toArray();
    }

    public static void main(final String[] args)
    {
        final BaseballElimination division = new BaseballElimination(args[0]);
        for (final String team : division.teams())
        {
            if (division.isEliminated(team))
            {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (final String t : division.certificateOfElimination(team))
                {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else
            {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
