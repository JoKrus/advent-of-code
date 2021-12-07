package net.jcom.adventofcode;

import net.jcom.adventofcode.aoc2019.misc.Opcoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class Sandbox {
    public static void main(String[] args) {
        String input = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().
                        getResourceAsStream(getFileNameOfInput(7, 2021))))).
                lines().collect(Collectors.joining("\n"));
        Opcoder op = new Opcoder(input);
        op.runOpcode();
        while (!op.getOutput().isEmpty()) {
            System.out.print(Character.toString(Math.toIntExact(op.getOutput().poll())));
        }
    }

    public static final String getFileNameOfInput(int day, int year) {
        String suffix = ".txt";
        String className = "Day%4d_%02d".formatted(year, day);
        String yearPackageName = "aoc%04d".formatted(year);
        return yearPackageName + File.separator + className + suffix;
    }
}
