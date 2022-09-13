package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;
import java.util.Map;
//Spring MVC, Spring Boot, Mustache, Hibernate, Postgresql, Spring Security
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

    // This means to get the bean(object)(the objects that are managed by the Spring IoC container are called beans) called messageRepo
    @Autowired
    private MessageRepo messageRepo; //use MessageRepo to handle the data

    @Autowired
    private UserRepo userRepo;
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
    public String greeting(@AuthenticationPrincipal User user, Map<String, Object> model) {
        model.put("user", user.getUsername());

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
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
        Message message = new Message(text, tag, user); //створили обєкт месседжу(ентіті)//user for tracking client

        messageRepo.save(message); // зберегли дані в репу

        Iterable<Message> messages = messageRepo.findAll(); // взяли дані з репи

        model.put("messages", messages);//передали у модель

        return "main";
    }

    @PostMapping("filter") //<form method="post" action="filter"> - action mapping
    public String filter(@RequestParam String text, Map<String, Object> model) {//<input type="text" name="text"> == String text!
        Iterable<Message> messages = null;//iterable interface because findByTag returns Tag, FindAll List
        try {
            text = text.toLowerCase(Locale.ROOT).trim();
            if (messageRepo.findByTag(text).spliterator().getExactSizeIfKnown() == 0) throw new RuntimeException();
            messages = messageRepo.findByTag(text);
        } catch (RuntimeException e) {
            model.put("message", "Invalid tag");
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("remove")
    public String remove(@RequestParam String id, Map<String, Object> model) {

        try {
            messageRepo.deleteById(Long.parseLong(id.trim()));
        } catch (RuntimeException e) {
            model.put("message", "ID doesn't exist!");
        }

        model.put("messages", messageRepo.findAll());

        return "main";
    }

    @PostMapping("sortByIdDesc")
    public String sortByIdDesc(Map<String, Object> model) {

        List<Message> messages = (List<Message>) messageRepo.findAll();

        messageRepo.sortById(messages);

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("sortByIdAsc")
    public String sortByIdAsc(Map<String, Object> model) {

        List<Message> messages = (List<Message>) messageRepo.findAll();

        messageRepo.sortByIdAsc(messages);

        model.put("messages", messages);

        return "main";
    }
}

/*
  IN THIS CASE METHOD BODY IS DEPENDENT ON MUSTACHE view technology
  The implementation of the method body relies on a view technology (in this case, Thymeleaf)
  to perform server-side rendering of the HTML.
  Thymeleaf parses the greeting.mustache template and evaluates the th:text expression
  to render the value of the ${name} parameter that was set in the controller
 */