package de.leanovate.routegenerator.combinators;

public class Position implements Comparable<Position> {
    public final int line;

    public final int column;

    public Position(final int line, final int column) {

        this.line = line;
        this.column = column;
    }

    @Override
    public int compareTo(final Position that) {

        if (line == that.line) {
            if (column == that.column) {
                return 0;
            }
            return column < that.column ? -1 : 1;
        }
        return line < that.line ? -1 : 1;
    }


    @Override
    public String toString() {

        return "[line: " + line + ", column: " + column + "]";
    }
}
