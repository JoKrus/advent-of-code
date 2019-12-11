package de.djgames.jonas.adventofcode;

import de.djgames.jonas.adventofcode.aoc2019.Day2019_10;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Hello world!
 */
public class App {
    /*
    Day2019_1: 3231195, 4843929
    Day2019_2: 4090701, 6421
    Day2019_3: 248, 28580
    Day2019_4: 1653, 1133
    Day2019_5: 9654885, 7079459
    Day2019_6: 254447, 445
    Day2019_7: 24625, 36497698
    Day2019_8: 2193,
    1   11111 1  1 1111 1111
    1   11    1  1 1    1
     1 1 111  1111 111  111
      1  1    1  1 1    1
      1  1    1  1 1    1
      1  1111 1  1 1111 1
    Day2019_9: 3742852857, 73439
    */

    public static void main(String[] args) {
        Reflections reflections = new Reflections("de.djgames.jonas.adventofcode");
        TreeSet<Class<? extends Day>> challenges = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
        challenges.addAll(reflections.getSubTypesOf(Day.class));

        // runAll(challenges);
        // runSinglePart(Day2019_10.class, true);
        runSingle(Day2019_10.class);
        //runSinglePart(Day2019_9.class, true);
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
}
