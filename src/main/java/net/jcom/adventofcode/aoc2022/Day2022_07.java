package net.jcom.adventofcode.aoc2022;

import net.jcom.adventofcode.Day;

import java.util.*;

public class Day2022_07 extends Day {
    @Override
    public String part1Logic() {
        Directory root = buildFileSystem();

        long result = 0;

        Queue<Directory> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            var pop = queue.poll();
            if (pop.getSize() <= 100000) {
                result += pop.getSize();
            }
            queue.addAll(pop.subDirs);
        }

        return "" + result;
    }

    @Override
    public String part2Logic() {
        Directory root = buildFileSystem();

        long totalDisk = 70000000;
        long freeToBe = 30000000;

        long freeCurrently = totalDisk - root.getSize();
        long needToFree = freeToBe - freeCurrently;

        List<Long> results = new ArrayList<>();

        Queue<Directory> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            var pop = queue.poll();
            if (pop.getSize() >= needToFree) {
                results.add(pop.getSize());
            }
            queue.addAll(pop.subDirs);
        }

        return "" + results.stream().mapToLong(Long::longValue).min().getAsLong();
    }

    private Directory buildFileSystem() {
        List<String> list = new ArrayList<>(Arrays.stream(input.split("\n")).filter(s -> !s.equals("$ ls")).toList());

        String currentDir = list.remove(0).split(" ")[1];
        Directory current = new Directory(currentDir, null, new ArrayList<>(), new ArrayList<>());
        Directory root = current;

        for (var out : list) {
            if (out.startsWith("$ cd ")) {
                out = out.substring(5);
                if (out.equals("/")) {
                    current = root;
                } else if (out.equals("..")) {
                    current = current.parent;
                } else {
                    String finalOut = out;
                    current = current.subDirs.stream().filter(directory -> directory.name.equals(finalOut)).findFirst().get();
                }
            } else {
                var split = out.split(" ");
                if (split[0].equals("dir")) {
                    current.subDirs.add(new Directory(split[1], current, new ArrayList<>(), new ArrayList<>()));
                } else {
                    current.files.add(new File(split[1], Long.parseLong(split[0]), current));
                }
            }
        }
        return root;
    }


    private record Directory(String name, Directory parent, List<Directory> subDirs, List<File> files) {
        public long getSize() {
            return subDirs.stream().mapToLong(Directory::getSize).sum() + files.stream().mapToLong(File::size).sum();
        }
    }

    private record File(String name, long size, Directory parent) {
    }
}
