package de.leanovate.routergenerator.plugin;

import groovy.lang.Closure;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.DefaultSourceDirectorySet;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.util.ConfigureUtil;

public class RoutesSourceVirtualDirectoryImpl implements RoutesSourceVirtualDirectory {
    private final SourceDirectorySet routes;

    public RoutesSourceVirtualDirectoryImpl(String parentDisplayName, FileResolver fileResolver) {

        final String displayName = String.format("%s route source", parentDisplayName);
        this.routes = new DefaultSourceDirectorySet(displayName, fileResolver);
        this.routes.getFilter().include("**/*.routes");
    }

    @Override
    public SourceDirectorySet getRoutes() {

        return routes;
    }

    @Override
    public RoutesSourceVirtualDirectoryImpl routes(Closure closure) {

        ConfigureUtil.configure(closure, getRoutes());
        return this;
    }
}
