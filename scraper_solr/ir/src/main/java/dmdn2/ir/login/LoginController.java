package dmdn2.ir.login;


import dmdn2.ir.user.*;

import spark.*;
import java.util.*;

public class LoginController {
    // Renders a template given a model and a request
    // The request is needed to check the user session for language settings
    // and to see if the user is logged in


    public static Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        model.put("loggedOut", loggedOut != null);

        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        model.put("loginRedirect", loginRedirect);

        return "login page";
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
            if (!UserController.authenticate(request.queryParams("username"), request.queryParams("password"))) {
            return "autentication fail";
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser",request.queryParams("username"));
        return "login ok";
    };



    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);

        return "logged out";
    };

    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
    public static Boolean IsLoggedIn(Request request) {
        if (request.session().attribute("currentUser") == null) {
            return false;
        }
        return true;
    }

}