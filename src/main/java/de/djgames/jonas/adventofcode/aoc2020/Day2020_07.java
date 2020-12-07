package de.djgames.jonas.adventofcode.aoc2020;

import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day2020_07 extends Day {
    @Override
    public String part1Logic() {
        String search = "shinygold";
        HashMap<String, List<String>> colorToPossParents = new HashMap<>();
        HashMap<String, List<Pair<Integer, String>>> colorToMustChildren = new HashMap<>();
        doSomeAmazingStuff(colorToPossParents, colorToMustChildren);

        Deque<String> deque = new ArrayDeque<>(Collections.singletonList(search));
        Set<String> visited = new HashSet<>();
        int containingSearch = -1; // -1 because shinygoldbag is added as 0

        while (!deque.isEmpty()) {
            String moved = deque.remove();
            boolean freshAdd = visited.add(moved);
            if (freshAdd) {
                deque.addAll(colorToPossParents.getOrDefault(moved, Collections.emptyList()));
                containingSearch++;
            }
        }

        return String.format("%d", containingSearch);
    }

    private void doSomeAmazingStuff(final HashMap<String, List<String>> colorToPossParents,
                                    final HashMap<String, List<Pair<Integer, String>>> colorToMustChildren) {
        Arrays.stream(input.split("\n")).forEach(s -> {
            // light red bags contain 1 bright white bag, 2 muted yellow bags.
            String[] splits = s.split(" ");
            String rootCol = splits[0] + splits[1];
            if (splits[4].equals("no")) {
                colorToMustChildren.put(rootCol, new ArrayList<>());
                //no bags inside, so not a possible parent to anyone
            } else {
                int idx = 4;
                List<Pair<Integer, String>> children = new ArrayList<>();
                while (idx < splits.length) {
                    int amount = Integer.parseInt(splits[idx]);
                    String col = splits[idx + 1] + splits[idx + 2];
                    children.add(Pair.of(amount, col));
                    idx += 4;
                }
                colorToMustChildren.put(rootCol, children);
                for (var child : children.stream().map(Pair::getRight).collect(Collectors.toList())) {
                    var existingList = colorToPossParents.getOrDefault(child, new ArrayList<>());
                    existingList.add(rootCol);
                    colorToPossParents.put(child, existingList);
                }
            }
        });
    }

    @Override
    public String part2Logic() {
        String search = "shinygold";
        HashMap<String, List<String>> colorToPossParents = new HashMap<>();
        HashMap<String, List<Pair<Integer, String>>> colorToMustChildren = new HashMap<>();
        doSomeAmazingStuff(colorToPossParents, colorToMustChildren);

        int ret = getAmountOfBagsInside(search, colorToMustChildren) - 1;

        return String.format("%d", ret);
    }

    private int getAmountOfBagsInside(String search,
                                      HashMap<String, List<Pair<Integer, String>>> colorToMustChildren) {
        int ret = 1;
        for (var child : colorToMustChildren.get(search)) {
            ret += child.getLeft() * getAmountOfBagsInside(child.getRight(), colorToMustChildren);
        }
        return ret;
    }
}
