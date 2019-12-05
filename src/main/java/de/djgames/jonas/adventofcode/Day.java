package de.djgames.jonas.adventofcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Day {
    protected String input;

    public void readAndSetInput() {
        if (input == null) {
            input = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(Thread.currentThread().getContextClassLoader().
                            getResourceAsStream(getFileNameOfInput())))).
                    lines().collect(Collectors.joining("\n"));
        }
    }

    public String execute() {
        return getClass().getSimpleName() + ": " + part1() + ", " + part2();
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
        return getClass().getSimpleName() + ".txt";
    }
}
