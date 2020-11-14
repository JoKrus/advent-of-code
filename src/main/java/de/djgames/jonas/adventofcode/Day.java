package de.djgames.jonas.adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Day {
    protected String input;

    public final void readAndSetInput() {
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(Thread.currentThread().getContextClassLoader().
                            getResourceAsStream(getFileNameOfInput())))).
                    lines().collect(Collectors.joining("\n"));
        }
    }

    public final String execute() {
        String base = getClass().getSimpleName() + ": ";
        String part1 = "FAILED";
        try {
            part1 = part1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String part2 = "FAILED";
        try {
            part2 = part2();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base + part1 + ", " + part2;
    }

    public final String part1() {
        readAndSetInput();
        return part1Logic();
    }

    public final String part2() {
        readAndSetInput();
        return part2Logic();
    }

    public abstract String part1Logic();

    public abstract String part2Logic();

    public final String getFileNameOfInput() {
        String suffix = ".txt";
        String className = getClass().getSimpleName();
        String yearPackageName = getClass().getPackageName().substring(getClass().getPackageName().lastIndexOf(".") + 1);
        return yearPackageName + File.separator + className + suffix;
    }
}
