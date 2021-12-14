package net.jcom.adventofcode;

import net.jcom.adventofcode.aoc2021.Day2021_15;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Reflections reflections = new Reflections(App.class.getPackageName());
        TreeSet<Class<? extends Day>> challenges = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
        challenges.addAll(reflections.getSubTypesOf(Day.class));

        runSingleTime(Day2021_15.class);
    }

    public static void runSinglePart(Class<? extends Day> dayClass, boolean part1) {
        try {
            Day day = dayClass.getConstructor().newInstance();
            System.out.println(part1 ? day.part1() : day.part2());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void runSingle(Class<? extends Day> dayClass) {
        try {
            System.out.println(dayClass.getConstructor().newInstance().execute());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void runAll(TreeSet<Class<? extends Day>> challenges) {
        challenges.forEach(App::runSingle);
    }

    public static void runAllTime(TreeSet<Class<? extends Day>> challenges) {
        challenges.forEach(App::runSingleTime);
    }

    public static void runSingleTime(Class<? extends Day> dayClass) {
        try {
            Day day = dayClass.getConstructor().newInstance();
            StringBuilder answer = new StringBuilder().append(day.getClass().getSimpleName()).append(": ");
            day.readAndSetInput();
            long part1Start = System.nanoTime();
            String day1 = day.part1Logic();
            long part1End = System.nanoTime();
            day.readAndSetInput();
            long part2Start = System.nanoTime();
            String day2 = day.part2Logic();
            long part2End = System.nanoTime();
            String result = String.format("%s within %.3fms, %s within %.3fms", day1, (1. * part1End - part1Start) * 1e-6, day2,
                    (1. * part2End - part2Start) * 1e-6);
            System.out.println(answer.append(result));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
