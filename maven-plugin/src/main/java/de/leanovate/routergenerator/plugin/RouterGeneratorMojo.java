package de.leanovate.routergenerator.plugin;

import de.leanovate.routergenerator.RouteParser;
import de.leanovate.routergenerator.RouteRules;
import de.leanovate.routergenerator.builder.JavaFileBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class RouterGeneratorMojo extends AbstractMojo {
    @Component
    protected MavenProject project;

    @Parameter(property = "routesDirectory", defaultValue = "${project.basedir}/src/main/routes")
    protected File routesDirectory;

    @Parameter(property = "routesIncludes", defaultValue = "**/*.routes")
    protected String routesIncludes;

    @Parameter(property = "routesExcludes", defaultValue = "")
    protected String routesExcludes;

    @Parameter(property = "output", defaultValue = "${project.basedir}/target/generated-sources")
    protected File output;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (!routesDirectory.exists() || !routesDirectory.isDirectory()) {
            getLog().warn("Directory " + routesDirectory.getAbsolutePath() + " does not exists");
            return;
        }

        this.project.addCompileSourceRoot(output.getAbsolutePath());

        try {
            final List<File> routesFiles = FileUtils.getFiles(routesDirectory, routesIncludes, routesExcludes);

            for (File routesFile : routesFiles) {
                getLog().info("Generate routes for: " + routesFile.getAbsolutePath());
                RouteRules routeRules = RouteParser.parse(new String(Files.readAllBytes(routesFile.toPath()), "UTF-8"));
                String className = JavaFileBuilder.makeClassName(routesFile.getName());
                try (JavaFileBuilder javaFileBuilder = new JavaFileBuilder(output, routeRules.packageName, className)) {
                    routeRules.build(javaFileBuilder);
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("IOException", e);
        }
    }
}
