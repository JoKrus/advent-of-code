package de.djgames.jonas.adventofcode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
        
        runAll(challenges);
    }

    public static void runAll(TreeSet<Class<? extends Day>> challenges) {
        challenges.forEach(day -> {
            try {
                System.out.println(day.getConstructor().newInstance().execute());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
        });
    }


    static class Node {
        TreeSet<String> requires = new TreeSet<>();
        TreeSet<String> enables = new TreeSet<>();
        String ident;
        int duration;

        public Node(String ident) {
            this.ident = ident;
            if (!ident.isEmpty())
                duration = 60 + ident.toCharArray()[0] + 1 - 'A';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return new EqualsBuilder().append(ident, node.ident).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(ident).toHashCode();
        }

        @Override
        public String toString() {
            return "Node{" + "requires=" + requires + ", enables=" + enables + ", ident='" + ident + '\'' + '}';
        }
    }
}