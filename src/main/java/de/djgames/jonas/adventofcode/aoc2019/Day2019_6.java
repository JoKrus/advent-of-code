package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2019_6 extends Day {
    @Override
    public String part1Logic() {
        Triple<Node, List<Node>, Map<String, List<String>>> things = getThings();

        int orbitcount = things.getMiddle().stream().mapToInt(Node::getCount).sum();

        return String.format("%d", orbitcount);
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
        Map<String, List<String>> orbitter = new HashMap<>();

        for (String s : orbitList) {
            String[] arg = s.split(Pattern.quote(")"));
            List<String> vals = orbitter.getOrDefault(arg[0], new ArrayList<>());
            vals.add(arg[1]);
            orbitter.put(arg[0], vals);
        }

        Node root = new Node(null, "COM", orbitter);

        List<Node> nodes = new ArrayList<>();
        root.getAllNodes(nodes);

        return Triple.of(root, nodes, orbitter);
    }

    private static class Node {
        Node parent;
        String self;
        List<Node> childs;

        public Node(Node parent, String self, Map<String, List<String>> orbitter) {
            this.parent = parent;
            this.self = self;
            childs = new ArrayList<>();
            if (orbitter.get(self) != null && !orbitter.get(self).isEmpty()) {
                orbitter.get(self).forEach(s -> this.childs.add(new Node(this, s, orbitter)));
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
            if (!childs.isEmpty()) {
                childs.forEach(node -> node.getAllNodes(list));
            }
        }

        private Node deepContains(Node n) {
            if (childs.contains(n)) {
                return this;
            } else if (!this.childs.isEmpty()) {
                for (Node start : this.childs) {
                    boolean[] boolref = {false};
                    start.deepContains(n, boolref);
                    if (boolref[0])
                        return start;
                }
            }
            return null;
        }

        private void deepContains(Node n, boolean[] found) {
            if (childs.contains(n)) {
                found[0] = true;
            } else if (!this.childs.isEmpty()) {
                for (Node node : childs) {
                    node.deepContains(n, found);
                }
            }
        }

        public void moveNodeToParent() {
            this.parent.parent.childs.add(this);
            this.parent.childs.remove(this);
            this.parent = parent.parent;
        }

        public void moveNodeToChild(Node child) {
            parent.childs.remove(this);
            child.childs.add(this);
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
                    ", childs=" + childs.stream().map(node -> node.self).collect(Collectors.toList()) +
                    '}';
        }
    }
}
