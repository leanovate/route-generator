package de.leanovate.routergenerator.plugin;

import de.leanovate.routergenerator.RouteParser;
import de.leanovate.routergenerator.RouteRules;
import de.leanovate.routergenerator.builder.JavaFileBuilder;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Files;

public class GenerateRoutesTask extends SourceTask {
    private File out = getProject().file("build/generated-src/routes");

    @TaskAction
    public void invokeGenerate() throws Exception {

        for (final File routesFile : getSource().getFiles()) {
            RouteRules routeRules = RouteParser.parse(new String(Files.readAllBytes(routesFile.toPath()), "UTF-8"));
            String className = JavaFileBuilder.makeClassName(routesFile.getName());
            try (JavaFileBuilder javaFileBuilder = new JavaFileBuilder(out, routeRules.packageName, className)) {
                routeRules.build(javaFileBuilder);
            }
        }
    }

    public void setOutputDirectory(final File outputDirectory) {

        this.out = outputDirectory;
    }

    @OutputDirectory
    public File getOutputDirectory() {

        return out;
    }
}
