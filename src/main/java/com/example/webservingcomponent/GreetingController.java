package com.example.webservingcomponent;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * GreetingController class handling HTTP requests (@ Controller to identify controller)
 * In this example GreetingController handles GET requests for /greeting by returning the name of a View (in this case, greeting)
 * A View is responsible for rendering the HTML content
 */

@Controller
public class GreetingController {

    /**
     * @ GetMapping annotation maps /greeting HTTP GET requests to the greeting() method.
     * @ RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method
     * This query string parameter is not required. If it is absent in the request, the defaultValue of "World" is used
     * The value of the name parameter is added to a Model object, ultimately making it accessible to the view template.
     *
     * localHost:8080/greeting?name=myName
     */

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    /**
     * "main" method doesn't influence mapping calls
     * model.put() is assigning value "Hello, it's me Mario" to "something" mustache element
     * return "main"; - specifies main.mustache file for model
     *
     * localHost:8080
     */

    @GetMapping("greeting/abobus")
    public String abobus(Map<String, Object> model){
        model.put("something", "ABOBUS");
        return "main";
    }

    @GetMapping("greeting/abobus/impostor")
    public String impostor(@RequestParam(name = "impostor", required = false, defaultValue = "PinkDude") String impostor, Map<String, Object> model){
        model.put("impostor", impostor);
        return "impostor";
    }

    @GetMapping("greeting/abobus/impostor/sussy")
    public String sussy(@RequestParam(name = "name") String sussyDude, Map<String, Object> model){
        model.put("name", sussyDude); //sussy?name=sussyDude
        return "sussy";
    }

}


/**
 * IN THIS CASE METHOD BODY IS DEPENDENT ON MUSTACHE view technology
 * The implementation of the method body relies on a view technology (in this case, Thymeleaf)
 * to perform server-side rendering of the HTML.
 * Thymeleaf parses the greeting.mustache template and evaluates the th:text expression
 * to render the value of the ${name} parameter that was set in the controller
 */