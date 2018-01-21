import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WordNet
{
    private final SAP sap;
    private final Map<String, List<Integer>> nounToSynsetsMap = new HashMap<>();
    private final List<String> synsetIdList = new ArrayList<>();

    public WordNet(final String synsets, final String hypernyms)
    {
        validateArgs(synsets, hypernyms);
        sap = readWordNet(synsets, hypernyms);
    }

    public Iterable<String> nouns()
    {
        return nounToSynsetsMap.keySet();
    }

    public boolean isNoun(final String word)
    {
        validateArgs(word);
        return nounToSynsetsMap.containsKey(word);
    }

    public int distance(final String nounA, final String nounB)
    {
        validateNouns(nounA, nounB);
        return sap.length(nounToSynsetsMap.get(nounA), nounToSynsetsMap.get(nounB));
    }

    public String sap(final String nounA, final String nounB)
    {
        validateNouns(nounA, nounB);
        return synsetIdList.get(sap.ancestor(nounToSynsetsMap.get(nounA), nounToSynsetsMap.get(nounB)));
    }

    private SAP readWordNet(final String synsets, final String hypernyms)
    {
        final Digraph digraph = readHypernyms(readSynsets(synsets), hypernyms);

        validateAcyclic(digraph);
        validateSingleRoot(digraph);

        return new SAP(digraph);
    }

    private int readSynsets(final String synsets)
    {
        final In synsetsIn = new In(synsets);
        int numberOfSynsets = 0;

        while (synsetsIn.hasNextLine())
        {
            final String[] columns = synsetsIn.readLine().split(",");
            final int synsetId = Integer.parseInt(columns[0]);
            synsetIdList.add(synsetId, columns[1]);
            mapNounsToSynsetId(synsetId, columns[1]);
            numberOfSynsets++;
        }

        return numberOfSynsets;
    }

    private void mapNounsToSynsetId(final int synsetId, final String synset)
    {
        Arrays.stream(synset.split("\\s"))
            .forEach(noun -> nounToSynsetsMap.merge(noun, Collections.singletonList(synsetId), this::mergeSynsetIds));
    }

    private List<Integer> mergeSynsetIds(final List<Integer> currentList, final List<Integer> newList)
    {
        final List<Integer> merged = new ArrayList<>(currentList);
        merged.addAll(newList);
        return merged;
    }

    private Digraph readHypernyms(final int vertices, final String hypernyms)
    {
        final Digraph digraph = new Digraph(vertices);
        final In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine())
        {
            final String[] columns = hypernymsIn.readLine().split(",");
            final int from = Integer.parseInt(columns[0]);

            Arrays.stream(columns, 1, columns.length)
                .map(Integer::parseInt)
                .forEach(to -> digraph.addEdge(from, to));
        }

        return digraph;
    }

    private void validateSingleRoot(final Digraph digraph)
    {
        int roots = 0;
        for (int index = 0; index < digraph.V(); index++)
        {
            if (digraph.outdegree(index) == 0)
            {
                roots++;
            }
            if (roots > 1)
            {
                throw new IllegalArgumentException();
            }
        }
    }

    private void validateAcyclic(final Digraph digraph)
    {
        if (new DirectedCycle(digraph).hasCycle())
        {
            throw new IllegalArgumentException();
        }
    }

    private void validateArgs(final Object... args)
    {
        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull))
        {
            throw new IllegalArgumentException();
        }
    }

    private void validateNouns(final String nounA, final String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB))
        {
            throw new IllegalArgumentException();
        }
    }
}
