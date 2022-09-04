package com.example.webservingcomponent;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * .@RestController combines @Controller and @ResponseBody,
 * web request returning data NOT a view
 */
@RestController
public class ControllerClass {

    @GetMapping("/")
    public String index(){
        return "Greeting from ABOBUS!";
    }

}
