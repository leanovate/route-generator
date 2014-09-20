package de.leanovate.router;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class UriBuilder {
    final StringBuilder uri = new StringBuilder();

    final List<String> queryParams = new ArrayList<>();

    public UriBuilder path(final String path) {

        uri.append(path);
        return this;
    }

    public UriBuilder segment(final String value) {

        try {
            uri.append('/').append(URLEncoder.encode(value, "UTF-8"));
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public UriBuilder query(final String name, Optional<String> optValue) {

        optValue.ifPresent((value) -> {
            try {
                queryParams.add(String
                        .format("%s=%s", URLEncoder.encode(name, "UTF-8"), URLEncoder.encode(value, "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }

    public UriBuilder query(final String name, OptionalInt optValue) {

        optValue.ifPresent((value) -> {
            try {
                queryParams.add(String
                        .format("%s=%s", URLEncoder.encode(name, "UTF-8"), String.valueOf(value)));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        return this;
    }

    public String build() {

        String delim = "?";
        for (String queryParam : queryParams) {
            uri.append(delim).append(queryParam);
            delim = "&";
        }
        return uri.toString();
    }
}
