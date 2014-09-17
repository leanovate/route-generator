package de.leanovate.routegenerator.builder;

import java.io.PrintWriter;
import java.util.function.Consumer;

public class IdentBuilder {
    public static String DEFAULT_IDENT = "    ";

    protected final String ident;

    protected final PrintWriter out;

    protected IdentBuilder(final PrintWriter out, final String ident) {

        this.out = out;
        this.ident = ident;
    }

    public void writeLine(final String line) {

        out.print(ident);
        out.println(line);
    }

    public void ident(final Consumer<IdentBuilder> body) {

        body.accept(new IdentBuilder(out, ident + DEFAULT_IDENT));
    }
}
