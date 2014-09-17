package de.leanovate.routegenerator.combinators;

import static de.leanovate.routegenerator.combinators.ParseResultMatchers.hasNoMoreInput;
import static de.leanovate.routegenerator.combinators.ParseResultMatchers.hasResult;
import static de.leanovate.routegenerator.combinators.ParseResultMatchers.wasSuccessful;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CalculatorTest {
    Calculator calculator = new Calculator();

    @Test
    public void testNumber() {
        final Parser<CharInput, Integer> number = calculator.mumber();

        assertThat(number.apply(new CharSequenceInput("123456")), allOf(wasSuccessful(), hasNoMoreInput(),
                hasResult(equalTo(123456))));
    }
}
