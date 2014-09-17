package de.leanovate.routergenerator.builder;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

public class JavaFileBuilder implements Closeable {
    public final File javaFile;

    public final String className;

    public final PrintWriter out;

    public JavaFileBuilder(final File outputDir, final String packageName, final String className) throws IOException {

        final File packageDir = new File(outputDir, packageName.replace('.', File.separatorChar));

        packageDir.mkdirs();

        this.className = className;
        javaFile = new File(packageDir, className + ".java");
        out = new PrintWriter(javaFile, "UTF-8");

        out.println(String.format("package %s;", packageName));
        out.println();
    }

    public void addImport(final String imp) {

        out.println(String.format("import %s;", imp));
    }

    public void publicClass(final String extensions, final Consumer<JavaClassBuilder> body) {

        out.println();
        out.println(String.format("public class %s%s {", className, extensions));
        body.accept(new JavaClassBuilder(out, IdentBuilder.DEFAULT_IDENT));
        out.println("}");
    }

    public void publicAbstractClass(final String extensions, final Consumer<JavaClassBuilder> body) {

        out.println();
        out.println(String.format("public abstract class %s%s {", className, extensions));
        body.accept(new JavaClassBuilder(out, IdentBuilder.DEFAULT_IDENT));
        out.println("}");
    }

    @Override
    public void close() throws IOException {

        out.close();
    }

    public static String makeClassName(String str) {

        final StringBuilder result = new StringBuilder();
        boolean upper = false;

        for (char ch : str.toCharArray()) {
            if (result.length() == 0) {
                if (Character.isJavaIdentifierStart(ch)) {
                    result.append(Character.toUpperCase(ch));
                }
            } else if (Character.isJavaIdentifierPart(ch)) {
                if (upper) {
                    result.append(Character.toUpperCase(ch));
                    upper = false;
                } else {
                    result.append(ch);
                }
            } else {
                upper = true;
            }
        }
        return result.toString();
    }
}
