package com.example.webservingcomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * run Debug to launch Spring app
 * Copy TomcatPort, localhost:8080
 * add mapping /greeting
 * /greeting?name=Valentyn
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
