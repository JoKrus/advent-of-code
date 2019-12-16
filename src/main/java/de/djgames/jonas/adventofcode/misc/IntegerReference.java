package de.djgames.jonas.adventofcode.misc;

import java.util.Objects;

public class IntegerReference {
    public int intValue;

    public IntegerReference(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerReference that = (IntegerReference) o;
        return intValue == that.intValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue);
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }
}
