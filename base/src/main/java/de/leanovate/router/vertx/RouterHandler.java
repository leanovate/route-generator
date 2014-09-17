package de.leanovate.router.vertx;

import de.leanovate.router.Router;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;

public class RouterHandler implements Handler<HttpServerRequest> {
    protected final Router<HttpServerRequest, HttpServerResponse> router;

    public RouterHandler(final Router<HttpServerRequest, HttpServerResponse> router) {

        this.router = router;
    }

    @Override
    public void handle(final HttpServerRequest request) {

        final VertxServerRequestAdapter adapter = new VertxServerRequestAdapter(request);

        if (!router.route(adapter.createContext())) {
            notFound(request);
        }
    }

    protected void notFound(final HttpServerRequest request) {

        request.response().setStatusCode(404).setStatusMessage("Not found").end();
    }
}
