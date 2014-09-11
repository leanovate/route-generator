package de.leanovate.router;

import java.util.Optional;

public interface RequestAdapter<Q, R> {
    String getPath();

    String getMethod();

    Optional<String> getQueryParam(String name);

    Q getRequest();

    R getResponse();
}
