package net.jcom.adventofcode.aoc2020;

import net.jcom.adventofcode.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2020_04 extends Day {
    @Override
    public String part1Logic() {
        long count = Arrays.stream(input.split("\n\n")).map(new PassportParser()).filter(Objects::nonNull).count();
        return "" + count;
    }

    @Override
    public String part2Logic() {
        long count = Arrays.stream(input.split("\n\n")).map(new PassportParser2()).filter(Objects::nonNull).count();
        return "" + count;
    }

    private class PassportParser implements Function<String, Passport> {

        @Override
        public Passport apply(String s) {
            String[] keyValPairs = s.split("\\s+");
            Map<String, String> map = Arrays.stream(keyValPairs).map(s1 -> s1.split(":", 2))
                    .collect(Collectors.toMap(strings -> strings[0],
                            strings -> strings[1]));

            String byr = map.get("byr"),
                    iyr = map.get("iyr"),
                    eyr = map.get("eyr"),
                    hgt = map.get("hgt"),
                    hcl = map.get("hcl"),
                    ecl = map.get("ecl"),
                    pid = map.get("pid"),
                    cid = map.getOrDefault("cid", "");

            if (byr == null) {
                return null;
            } else if (iyr == null) {
                return null;
            } else if (eyr == null) {
                return null;
            } else if (hgt == null) {
                return null;
            } else if (hcl == null) {
                return null;
            } else if (ecl == null) {
                return null;
            } else if (pid == null) {
                return null;
            } else if (cid == null) {
                return null;
            } else {
                return new Passport(byr, iyr, eyr, hgt, hcl, ecl, pid, cid);
            }
        }
    }

    private class PassportParser2 implements Function<String, Passport> {

        @Override
        public Passport apply(String s) {
            String[] keyValPairs = s.split("\\s+");
            Map<String, String> map = Arrays.stream(keyValPairs).map(s1 -> s1.split(":", 2))
                    .collect(Collectors.toMap(strings -> strings[0],
                            strings -> strings[1]));

            String byr = map.get("byr"),
                    iyr = map.get("iyr"),
                    eyr = map.get("eyr"),
                    hgt = map.get("hgt"),
                    hcl = map.get("hcl"),
                    ecl = map.get("ecl"),
                    pid = map.get("pid"),
                    cid = map.getOrDefault("cid", "");

            if (byr == null) {
                return null;
            } else if (iyr == null) {
                return null;
            } else if (eyr == null) {
                return null;
            } else if (hgt == null) {
                return null;
            } else if (hcl == null) {
                return null;
            } else if (ecl == null) {
                return null;
            } else if (pid == null) {
                return null;
            } else if (cid == null) {
                return null;
            }

            int byrInt = Integer.parseInt(byr);
            if (byrInt < 1920 || byrInt > 2002)
                return null;

            int iyrInt = Integer.parseInt(iyr);
            if (iyrInt < 2010 || iyrInt > 2020)
                return null;

            int eyrInt = Integer.parseInt(eyr);
            if (eyrInt < 2020 || eyrInt > 2030)
                return null;

            String hgtUnit = hgt.substring(hgt.length() - 2);

            if (hgtUnit.equals("cm")) {
                int amount = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
                if (amount > 193 || amount < 150)
                    return null;
            } else if (hgtUnit.equals("in")) {
                int amount = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
                if (amount > 76 || amount < 59)
                    return null;
            } else
                return null;


            //hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.

            hcl = hcl.toLowerCase();
            if (hcl.charAt(0) != '#')
                return null;
            Pattern hclPattern = Pattern.compile("([0-9]|[a-f]){6}");
            Matcher hclMatcher = hclPattern.matcher(hcl.substring(1));
            if (!hclMatcher.matches())
                return null;
            //([0-9]|[a-f]){6}

            List<String> validEyes = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
            if (validEyes.stream().noneMatch(s1 -> s1.equals(ecl)))
                return null;

            Pattern pidPattern = Pattern.compile("[0-9]{9}");
            Matcher pidMatcher = pidPattern.matcher(pid);
            if (!pidMatcher.matches()) return null;

            return new Passport(byr, iyr, eyr, hgt, hcl, ecl, pid, cid);

        }
    }


    private static class Passport {
        final String byr, iyr, eyr, hgt, hcl, ecl, pid, cid;

        public Passport(String byr, String iyr, String eyr, String hgt, String hcl, String ecl, String pid, String cid) {
            this.byr = byr;
            this.iyr = iyr;
            this.eyr = eyr;
            this.hgt = hgt;
            this.hcl = hcl;
            this.ecl = ecl;
            this.pid = pid;
            this.cid = cid;
        }
    }
}
