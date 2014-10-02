package de.leanovate.routergenerator.builder;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

public class JavaClassBuilder extends IdentBuilder {

    public JavaClassBuilder(final PrintWriter out, final String ident) {

        super(out, ident);
    }

    public void publicMethod(final String returnType, final String name, final List<String> parameters,
            Consumer<JavaMethodBuilder> body) {

        out.println();
        writeLine(String.format("public %s %s(%s) {", returnType, name, String.join(", ", parameters)));
        body.accept(new JavaMethodBuilder(out, ident + DEFAULT_IDENT));
        writeLine("}");
    }

    public void publicStaticMethod(final String returnType, final String name, final List<String> parameters,
            Consumer<JavaMethodBuilder> body) {

        out.println();
        writeLine(String.format("public static %s %s(%s) {", returnType, name, String.join(", ", parameters)));
        body.accept(new JavaMethodBuilder(out, ident + DEFAULT_IDENT));
        writeLine("}");
    }

    public void protectedAbstractMethod(final String returnType, final String name, final List<String> parameters) {

        out.println();
        writeLine(String.format("protected abstract %s %s(%s);", returnType, name, String.join(", ", parameters)));
    }

    public void publicStaticInnerClass(final String name, final Consumer<JavaClassBuilder> body) {

        out.println();
        writeLine(String.format("public static class %s {", name));
        body.accept(new JavaClassBuilder(out, ident + DEFAULT_IDENT));
        writeLine("}");
    }

    public void publicConstant(final String name, final String value) {

        out.println();
        writeLine(String.format("public static final String %s = \"%s\";", name, value));
    }
}
