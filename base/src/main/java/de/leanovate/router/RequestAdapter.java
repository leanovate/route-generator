package de.leanovate.router;

import java.util.Optional;

public interface RequestAdapter<T> {
    String getPath();

    String getMethod();

    Optional<String> getQueryParam(String name);

    T getRequest();
}
