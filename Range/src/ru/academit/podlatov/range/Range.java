package ru.academit.podlatov.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        if (from > to) {
            throw new IllegalArgumentException("\"from\" cannot be greater than \"to\".");
        }

        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range getIntersection(Range range) {
        double maxFrom = Math.max(from, range.from);
        double minTo = Math.min(to, range.to);

        if (maxFrom >= minTo) {
            return null;
        }

        return new Range(maxFrom, minTo);
    }

    public Range[] getUnion(Range range) {
        double maxFrom = Math.max(from, range.from);
        double maxTo = Math.max(to, range.to);

        double minFrom = Math.min(from, range.from);
        double minTo = Math.min(to, range.to);

        if (maxFrom > minTo) {
            return new Range[]{new Range(minFrom, minTo), new Range(maxFrom, maxTo)};
        }

        return new Range[]{new Range(minFrom, maxTo)};
    }

    public Range[] getDifference(Range range) {
        double maxFrom = Math.max(from, range.from);
        double minTo = Math.min(to, range.to);

        if (maxFrom > minTo) {
            return new Range[]{new Range(from, to)};
        }
        if (from >= range.from && to <= range.to) {
            return new Range[]{};
        }
        if (from < range.from && to > range.to) {
            return new Range[]{new Range(from, range.from), new Range(range.to, to)};
        }
        if (from <= range.to && from >= range.from) {
            return new Range[]{new Range(range.to, to)};
        }

        return new Range[]{new Range(from, range.from)};
    }

    @Override
    public String toString() {
        return "Range(from=" + from + ", to=" + to + ")";
    }
}