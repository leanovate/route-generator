package de.leanovate.routergenerator.combinators;

import static de.leanovate.routergenerator.combinators.CharParsers.elem;
import static de.leanovate.routergenerator.combinators.CoreParsers.oneOrMore;

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
