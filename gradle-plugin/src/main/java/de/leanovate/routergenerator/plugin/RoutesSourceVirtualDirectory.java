package de.leanovate.routergenerator.plugin;

import groovy.lang.Closure;
import org.gradle.api.file.SourceDirectorySet;

public interface RoutesSourceVirtualDirectory {
    SourceDirectorySet getRoutes();

    RoutesSourceVirtualDirectory routes(Closure closure);
}
