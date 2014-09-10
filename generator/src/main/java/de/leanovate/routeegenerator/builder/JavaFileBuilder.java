package de.leanovate.routeegenerator.builder;

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

    public void publicClass(final Consumer<JavaClassBuilder> body) {

        out.println(String.format("public class %s {", className));
        body.accept(new JavaClassBuilder(out, IdentBuilder.DEFAULT_IDENT));
        out.println("}");
    }

    public void publicAbstractClass(final Consumer<JavaClassBuilder> body) {

        out.println(String.format("public abstract class %s {", className));
        body.accept(new JavaClassBuilder(out, IdentBuilder.DEFAULT_IDENT));
        out.println("}");
    }

    @Override
    public void close() throws IOException {

        out.close();
    }
}
