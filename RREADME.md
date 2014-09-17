route-generator
===============

# Introduction

This is a very generic framework to use Rails-like (or Play framework like) http route files in any java-based web-framework of your choice, including (but not restricted to) plain old java servlets.

## How it works

Consider you have a class `SomethingController` like this:

~~~java
pacakge com.myself.project

public class SomethingController {
  public static void index(Response response) {
    ... do something ...
  }

  public static void create(Request request, Response response) {
    ... do something ...
  }
  
  public static void get(String id, Response response) {
    ... do something ...
  }
}
~~~

Wheras `Request` and `Response` are the request and response classes of your web-framework (line `HttpServletRequest` and `HttpServletResponse` in case of servlets).

Now you can define somewhere inside your project (default: src/main/routes) one or more .routes files like this:

something.routes
~~~
package com.myself.project

GET  /something      SomethingController.index(response)
POST /something      SomethingController.create(request, response)
GET  /something/:id  SomethingController.get(id, response)
~~~

which will be converted to a java class

~~~java
pacakge com.myself.project

import de.leanovate.router.RouteMatchingContext;
import static de.leanovate.router.CommonRouteRules.*;

public class SomethingRoutes {
  public boolean route(final RouteMatchingContext<Request, Response> ctx0) {
    ... if request matches the rules: calles the methods of SomethingController and returns true
    ... otherwise returns false
  }
}
~~~

Its now up to you to integrate this class into your web-framework, by using the approriate adapters or implement them yourself, which should be fairly straight foreward in both cases.

At the moment the following adapters are provided out of the box:
* Servlet-API 3.1
* Vert.x


# Licence

[MIT Licence](http://opensource.org/licenses/MIT)