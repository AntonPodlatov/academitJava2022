package ru.academit.podlatov.range_main;

import ru.academit.podlatov.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(-5, -1);
        Range range2 = new Range(-2, 10);

        System.out.println(range1);
        System.out.println("Length: " + range1.getLength());
        System.out.println("-6 is inside: " + range1.isInside(-6));
        System.out.println();
        System.out.println("Intersection with " + range2 + ": " + range1.getIntersection(range2));
        System.out.println();

        Range[] rangesUnion = range1.getUnion(range2);
        for (Range r : rangesUnion) {
            System.out.println("Union:" + r);
        }
        System.out.println();

        Range[] rangesDifference = range1.getDifference(range2);
        for (Range r : rangesDifference) {
            System.out.println("Difference: " + r);
        }
    }
}
