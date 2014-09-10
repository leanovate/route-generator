package de.leanovate.routeegenerator.model;

import java.util.ArrayList;
import java.util.List;

public class PathRoutePattern extends RoutePattern {
    public List<RoutePattern> children = new ArrayList<>();

    public PathRoutePattern() {
    }
}
