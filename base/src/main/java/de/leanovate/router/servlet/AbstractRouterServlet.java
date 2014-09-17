package de.leanovate.router.servlet;

import de.leanovate.router.Router;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class AbstractRouterServlet extends GenericServlet {
    protected final Router<HttpServletRequest, HttpServletResponse> router;

    protected AbstractRouterServlet(final Router<HttpServletRequest, HttpServletResponse> router) {

        this.router = router;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {

        if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        final ServletRequestAdapter adapter =
                new ServletRequestAdapter((HttpServletRequest) req, (HttpServletResponse) res);

        if (!router.route(adapter.createContext())) {
            notFound((HttpServletRequest) req, (HttpServletResponse) res);
        }
    }

    protected void notFound(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        response.sendError(404, "Not found");
    }
}
