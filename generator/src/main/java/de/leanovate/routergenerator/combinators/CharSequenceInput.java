package de.leanovate.routergenerator.combinators;

public class CharSequenceInput extends CharInput {
    public static char EOF = '\032';

    private final CharSequence chars;

    private final int offset;

    private final int column;

    private final int line;

    public CharSequenceInput(final CharSequence chars) {

        this(chars, 0, 1, 1);
    }

    public CharSequenceInput(final CharSequence chars, final int offset, final int column, final int line) {

        this.chars = chars;
        this.offset = offset;
        this.column = column;
        this.line = line;
    }

    @Override
    public char getFirst() {

        if (isAtEnd()) {
            return EOF;
        }
        return chars.charAt(offset);
    }

    @Override
    public CharInput getRest() {

        if (isAtEnd()) {
            return this;
        } else if (chars.charAt(offset) == '\n') {
            return new CharSequenceInput(chars, offset + 1, 1, line + 1);
        } else {
            return new CharSequenceInput(chars, offset + 1, column + 1, line);
        }
    }

    @Override
    public Position getPosition() {

        return new Position(line, column);
    }

    @Override
    public boolean isAtEnd() {

        return offset == chars.length();
    }
}
