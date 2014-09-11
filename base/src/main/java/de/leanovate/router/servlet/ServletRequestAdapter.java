package de.leanovate.router.servlet;

import de.leanovate.router.RequestAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

public class ServletRequestAdapter implements RequestAdapter<HttpServletRequest, HttpServletResponse> {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ServletRequestAdapter(final HttpServletRequest request, final HttpServletResponse response) {

        this.request = request;
        this.response = response;
    }

    @Override
    public String getPath() {

        return request.getPathInfo();
    }

    @Override
    public String getMethod() {

        return request.getMethod();
    }

    @Override
    public Optional<String> getQueryParam(final String name) {

        return Optional.ofNullable(request.getParameter(name));
    }

    @Override
    public HttpServletRequest getRequest() {

        return request;
    }

    @Override
    public HttpServletResponse getResponse() {

        return response;
    }
}
