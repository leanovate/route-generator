package de.leanovate.routegenerator.combinators;

import static de.leanovate.routegenerator.combinators.CharParsers.elem;
import static de.leanovate.routegenerator.combinators.CoreParsers.oneOrMore;

public class Calculator {
    public Parser<CharInput, Integer> mumber() {

        return oneOrMore(digit()).map((digits) -> {
            final StringBuilder builder = new StringBuilder();
            digits.forEach(builder::append);
            return Integer.parseInt(builder.toString());
        });
    }

    public Parser<CharInput, Character> digit() {

        return elem("digit", Character::isDigit);
    }
}
