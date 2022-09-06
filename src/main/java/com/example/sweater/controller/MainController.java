package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
//Spring MVC, Spring Boot, Mustache, Hibernate, Postgresql
/*
In Spring, the objects that are managed by the Spring IoC container are called beans.
 */

/**
 * MainController class handling HTTP requests (@ Controller to identify controller)
 * In this example MainController handles GET requests for /greeting by returning the name of a View (in this case, greeting)
 * A View is responsible for rendering the HTML content
 */

@Controller
public class MainController {

    @Autowired
    // This means to get the bean(object)(the objects that are managed by the Spring IoC container are called beans) called messageRepo
    private MessageRepo messageRepo; //use MessageRepo to handle the data

    /**
     * @Контроллер модуль програмний який по "/greeting" слухає запити і повертає відповідні дані (в нашій ситуації файл шаблон)
     */


    /**
     * @ GetMapping annotation maps /greeting HTTP GET requests to the greeting() method.
     * @ RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method
     * This query string parameter is not required. If it is absent in the request, the defaultValue of "World" is used
     * The value of the name parameter is added to a Model object, ultimately making it accessible to the view template.
     * <p>
     * localHost:8080/greeting?name=myName
     */

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    /**
     * model.put() is assigning String name variable to "name" mustache element
     * <p>return "greeting"; - specifies main.mustache file for view model
     */

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);//passing list to element "messages"

        return "main";//name of mustache file, which represents on HTTP GET request;
    }

    @PostMapping("/main")//<form method="post" action="add"> - action mapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag); //створили обєкт месседжу(ентіті)

        messageRepo.save(message); // зберегли дані в репу

        Iterable<Message> messages = messageRepo.findAll(); // взяли дані з репи

        model.put("messages", messages);//передали у модель

        return "main";
    }

    @PostMapping("filter") //<form method="post" action="filter"> - action mapping
    public String filter(@RequestParam String text, Map<String, Object> model) {//<input type="text" name="text"> == String text!
        Iterable<Message> messages;//iterable interface because findByTag returns Tag, FindAll List

        if (text != null && !text.isEmpty()) {
            messages = messageRepo.findByTag(text);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("remove")
    public String remove(@RequestParam String id, Map<String, Object> model) {

        messageRepo.deleteById(Long.parseLong(id));

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }
}

/**
 * IN THIS CASE METHOD BODY IS DEPENDENT ON MUSTACHE view technology
 * The implementation of the method body relies on a view technology (in this case, Thymeleaf)
 * to perform server-side rendering of the HTML.
 * Thymeleaf parses the greeting.mustache template and evaluates the th:text expression
 * to render the value of the ${name} parameter that was set in the controller
 */