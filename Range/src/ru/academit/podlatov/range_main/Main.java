package ru.academit.podlatov.range_main;

import ru.academit.podlatov.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(-5, -1);
        Range range1 = new Range(-2, 10);

        System.out.println(range);
        System.out.println("Length: " + range.getLength());
        System.out.println("-6 is inside: " + range.isInside(-6));
        System.out.println();
        System.out.println("Intersection with " + range1 + ": " + range.getIntersection(range1));
        System.out.println();

        Range[] rangesUnion = range.getUnion(range1);
        for (Range r : rangesUnion) {
            System.out.println("Union:" + r);
        }
        System.out.println();

        Range[] rangesDifference = range.getDifference(range1);
        for (Range r : rangesDifference) {
            System.out.println("Difference: " + r);
        }
    }
}
