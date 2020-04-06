package dmdn2.ir.login;


import dmdn2.ir.RandomStri;
import dmdn2.ir.user.*;

import org.json.JSONObject;
import spark.*;
import java.util.*;

public class LoginController {
    // Renders a template given a model and a request
    // The request is needed to check the user session for language settings
    // and to see if the user is logged in
    private  Map<String, Object> model = new HashMap<>();

    public Route serveLoginPage = (Request request, Response response) -> {

        if (request.cookie("user") ==null){
            response.cookie("user", RandomStri.randomAlphaNumeric(100));

        }else{
            if (IsLoggedIn(request)){
                response.redirect("/link_table_html/");
            }else{
                response.cookie("user", RandomStri.randomAlphaNumeric(100));
            }
        }

        response.redirect("/login/login.html");
        return null;
    };

    public Route handleLoginPost = (Request request, Response response) -> {


        JSONObject obj = new JSONObject(request.body());
        response.type("application/json");
            if (!UserController.authenticate(   obj.get("username").toString(),obj.get("password").toString())) {

                return "{\"autentication\":\"fail\"}";
        }

        model.put(request.cookie("user"),obj.get("username"));
        return "{\"autentication\":\"ok\"}";
    };



    public Route handleLogoutPost = (Request request, Response response) -> {
        response.type("application/json");
        model.remove(request.cookie("user"));
        return "{\"logout\":\"ok\"}";
    };

    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
    public Boolean IsLoggedIn(Request request) {
        if (model.get(request.cookie("user")) == null) {
            return false;
        }
        return true;
    }

}