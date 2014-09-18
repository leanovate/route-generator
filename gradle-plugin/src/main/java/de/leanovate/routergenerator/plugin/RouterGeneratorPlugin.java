package de.leanovate.routergenerator.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.internal.tasks.DefaultSourceSet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;

import javax.inject.Inject;

import java.io.File;

public class RouterGeneratorPlugin implements Plugin<Project> {
    private final FileResolver fileResolver;

    @Inject
    public RouterGeneratorPlugin(final FileResolver fileResolver) {

        this.fileResolver = fileResolver;
    }

    @Override
    public void apply(final Project project) {

        final Configuration routesConfig = project.getConfigurations().create("routes").setVisible(false);
        project.getConfigurations().getByName(JavaPlugin.COMPILE_CONFIGURATION_NAME).extendsFrom(routesConfig);

        project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().all((sourceSet) -> {

            final RoutesSourceVirtualDirectoryImpl routesSourceSet =
                    new RoutesSourceVirtualDirectoryImpl(((DefaultSourceSet) sourceSet).getDisplayName(),
                            fileResolver);
            new DslObject(sourceSet).getConvention().getPlugins().put("routes", routesSourceSet);
            final String srcDir = String.format("src/%s/routes", sourceSet.getName());
            routesSourceSet.getRoutes().srcDir(srcDir);
            sourceSet.getAllSource().source(routesSourceSet.getRoutes());

            final String taskName = sourceSet.getTaskName("generate", "Routes");
            final GenerateRoutesTask task = project.getTasks().create(taskName, GenerateRoutesTask.class);
            task.setDescription(String.format("Processes the %s Routes", sourceSet.getName()));

            task.setSource(routesSourceSet.getRoutes());

            final String outputDirectoryName =
                    String.format("%s/generated-src/routes/%s", project.getBuildDir(), sourceSet.getName());
            final File outputDirectory = new File(outputDirectoryName);
            task.setOutputDirectory(outputDirectory);
            sourceSet.getJava().srcDir(outputDirectory);

            project.getTasks().getByName(sourceSet.getCompileJavaTaskName()).dependsOn(taskName);
        });
    }
}
