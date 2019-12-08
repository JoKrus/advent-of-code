package de.djgames.jonas.adventofcode.aoc2019;

import de.djgames.jonas.adventofcode.Day;
import de.djgames.jonas.adventofcode.misc.Opcoder;
import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.stream.Collectors;

public class Day2019_7 extends Day {
    @Override
    public String part1Logic() {
        List<Integer> phaseTypeList = List.of(0, 1, 2, 3, 4);
        List<List<Integer>> permutations = Generator.permutation(phaseTypeList).simple().stream().collect(Collectors.toList());
        int max = 0;
        int[] arr = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        for (List<Integer> phaseList : permutations) {
            int out = 0;
            for (int phase : phaseList) {
                int[] array = Arrays.copyOf(arr, arr.length);
                out = Opcoder.runOpcodeLegacy(array, -1, phase, out);
            }
            if (out > max) max = out;
        }
        return String.format("%d", max);
    }

    @Override
    public String part2Logic() {
        List<Integer> phaseTypeList = List.of(5, 6, 7, 8, 9);
        List<List<Integer>> permutations = Generator.permutation(phaseTypeList).simple().stream().collect(Collectors.toList());

        Integer max = 0;
        int[] baseArray = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        for (List<Integer> phaseList : permutations) {
            ArrayList<Opcoder> opcoderList = new ArrayList<>();
            for (int i = 0; i < 5; ++i) {
                Opcoder opcoder = new Opcoder(baseArray);
                opcoder.addInput(phaseList.get(i));
                opcoderList.add(opcoder);
            }
            Queue<Opcoder> opcoderQ = new ArrayDeque<>(opcoderList);
            Queue<Integer> transfer = new ArrayDeque<>(Collections.singleton(0));
            int iters = -1;
            while (!opcoderQ.isEmpty() && iters < 50) {
                iters++;
                Opcoder opcoder = opcoderQ.poll();
                opcoder.addInput(transfer);
                opcoder.runOpcode();
                Opcoder.HaltTypes haltType = opcoder.getHaltType();

                if (haltType == Opcoder.HaltTypes.EXIT_WAIT) {
                    opcoderQ.offer(opcoder);
                }
                while (!opcoder.getOutput().isEmpty()) {
                    transfer.add(opcoder.getOutput().poll());
                }
            }

            if (transfer.element() > max) max = transfer.peek();
        }
        return String.format("%d", max);
    }
}
