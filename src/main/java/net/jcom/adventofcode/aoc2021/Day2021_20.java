package net.jcom.adventofcode.aoc2021;

import net.jcom.adventofcode.Day;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day2021_20 extends Day {
    @Override
    public String part1Logic() {
        String[] split = input.split("\n\n");
        var enhance = split[0];
        var image = split[1];

        HashMap<Point, Character> imagePixelsMap = new HashMap<>();

        var imageLines = image.split("\n");
        for (int y = 0; y < imageLines.length; y++) {
            var imageCols = imageLines[y].toCharArray();
            for (int x = 0; x < imageCols.length; x++) {
                var p = new Point(x, y);
                imagePixelsMap.put(p, imageCols[x]);
            }
        }

        for (int i = 0; i < 2; i++) {
            imagePixelsMap = iterateEnhancement(imagePixelsMap, enhance, i);
        }

        var mapVariant = imagePixelsMap.values().stream().filter(character -> character == '#').count();

        return "%d".formatted(mapVariant);
    }

    private HashMap<Point, Character> iterateEnhancement(HashMap<Point, Character> imagePixelsMap,
                                                         String enhance, int iteration) {
        char border = iteration % 2 == 1 ? enhance.charAt(0) : enhance.charAt(enhance.length() - 1);

        HashMap<Point, Character> ret = new HashMap<>();
        for (var entry : imagePixelsMap.keySet().stream().distinct()
                .map(this::getNeighborList).flatMap(Collection::stream)
                .collect(Collectors.toSet())) {
            var bin = getBinaryString(entry, imagePixelsMap, border);
            var number = Integer.parseInt(bin, 2);
            ret.put(entry, enhance.charAt(number));
        }

        return ret;
    }


    private String getBinaryString(Point entry, HashMap<Point, Character> imagePixelsMap, char border) {
        StringBuilder sb = new StringBuilder();
        int[] dx = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};
        int[] dy = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};

        for (int i = 0; i < dx.length; i++) {
            var p = new Point(entry.x + dx[i], entry.y + dy[i]);
            var charString = imagePixelsMap.getOrDefault(p, border);
            sb.append(charString == '#' ? "1" : "0");
        }

        return sb.toString();
    }

    private List<Point> getNeighborList(Point entry) {
        int[] dx = new int[]{-1, 0, 1, -1, 0, 1, -1, 0, 1};
        int[] dy = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};

        List<Point> ps = new ArrayList<>();
        for (int i = 0; i < dx.length; i++) {
            var p = new Point(entry.x + dx[i], entry.y + dy[i]);
            ps.add(p);
        }
        return ps;
    }

    @Override
    public String part2Logic() {
        String[] split = input.split("\n\n");
        var enhance = split[0];
        var image = split[1];

        HashMap<Point, Character> imagePixelsMap = new HashMap<>();

        var imageLines = image.split("\n");
        for (int y = 0; y < imageLines.length; y++) {
            var imageCols = imageLines[y].toCharArray();
            for (int x = 0; x < imageCols.length; x++) {
                var p = new Point(x, y);
                imagePixelsMap.put(p, imageCols[x]);
            }
        }

        for (int i = 0; i < 50; i++) {
            imagePixelsMap = iterateEnhancement(imagePixelsMap, enhance, i);
        }

        var mapVariant = imagePixelsMap.values().stream().filter(character -> character == '#').count();

        return "%d".formatted(mapVariant);

    }
}
