package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class ViewController {

    @GetMapping("/registration")
    public String showForm() {
        return "registration";
    }

    @GetMapping("/login")
    public String showFormLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String showFormIndex() {
        return "index";
    }

}
