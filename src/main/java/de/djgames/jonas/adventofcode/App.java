package de.djgames.jonas.adventofcode;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Reflections reflections = new Reflections("de.djgames.jonas.adventofcode");
        TreeSet<Class<? extends Day>> challenges = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
        challenges.addAll(reflections.getSubTypesOf(Day.class));

//        runSingle(Day2019_6.class);
        runAll(challenges);
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
