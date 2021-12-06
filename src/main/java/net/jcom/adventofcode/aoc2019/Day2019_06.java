package net.jcom.adventofcode.aoc2019;

import net.jcom.adventofcode.Day;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2019_06 extends Day {
    @Override
    public String part1Logic() {
        Triple<Node, List<Node>, Map<String, List<String>>> things = getThings();

        int orbitCount = things.getMiddle().stream().mapToInt(Node::getCount).sum();

        return String.format("%d", orbitCount);
    }

    @Override
    public String part2Logic() {
        Triple<Node, List<Node>, Map<String, List<String>>> triple = getThings();

        Node you = triple.getMiddle().stream().filter(node -> node.self.equals("YOU")).findFirst().orElse(null);
        Node san = triple.getMiddle().stream().filter(node -> node.self.equals("SAN")).findFirst().orElse(null);

        int dist = 0;
        if (you != null && san != null) {
            while (!you.parent.equals(san.parent)) {
                Node n;
                if ((n = you.parent.deepContains(san)) != null) {
                    you.moveNodeToChild(n);
                } else {
                    you.moveNodeToParent();
                }
                dist++;
            }
        }

        return String.format("%d", dist);
    }

    private Triple<Node, List<Node>, Map<String, List<String>>> getThings() {
        String[] orbitList = input.split("\n");
        Map<String, List<String>> orbiter = new HashMap<>();

        for (String s : orbitList) {
            String[] arg = s.split(Pattern.quote(")"));
            List<String> values = orbiter.getOrDefault(arg[0], new ArrayList<>());
            values.add(arg[1]);
            orbiter.put(arg[0], values);
        }

        Node root = new Node(null, "COM", orbiter);

        List<Node> nodes = new ArrayList<>();
        root.getAllNodes(nodes);

        return Triple.of(root, nodes, orbiter);
    }

    private static class Node {
        Node parent;
        String self;
        List<Node> children;

        public Node(Node parent, String self, Map<String, List<String>> orbiter) {
            this.parent = parent;
            this.self = self;
            children = new ArrayList<>();
            if (orbiter.get(self) != null && !orbiter.get(self).isEmpty()) {
                orbiter.get(self).forEach(s -> this.children.add(new Node(this, s, orbiter)));
            }
        }

        public int getCount() {
            int[] ret = new int[]{0};
            getCount(ret);
            return ret[0];
        }

        private int[] getCount(int[] i) {
            if (parent == null) {
                return i;
            } else {
                i[0]++;
                return parent.getCount(i);
            }
        }

        public void getAllNodes(List<Node> list) {
            list.add(this);
            if (!children.isEmpty()) {
                children.forEach(node -> node.getAllNodes(list));
            }
        }

        private Node deepContains(Node n) {
            if (children.contains(n)) {
                return this;
            } else if (!this.children.isEmpty()) {
                for (Node start : this.children) {
                    boolean[] boolRef = {false};
                    start.deepContains(n, boolRef);
                    if (boolRef[0])
                        return start;
                }
            }
            return null;
        }

        private void deepContains(Node n, boolean[] found) {
            if (children.contains(n)) {
                found[0] = true;
            } else if (!this.children.isEmpty()) {
                for (Node node : children) {
                    node.deepContains(n, found);
                }
            }
        }

        public void moveNodeToParent() {
            this.parent.parent.children.add(this);
            this.parent.children.remove(this);
            this.parent = parent.parent;
        }

        public void moveNodeToChild(Node child) {
            parent.children.remove(this);
            child.children.add(this);
            this.parent = child;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(self, node.self);
        }

        @Override
        public int hashCode() {
            return Objects.hash(self);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "self='" + self + '\'' +
                    ", children=" + children.stream().map(node -> node.self).collect(Collectors.toList()) +
                    '}';
        }
    }
}
