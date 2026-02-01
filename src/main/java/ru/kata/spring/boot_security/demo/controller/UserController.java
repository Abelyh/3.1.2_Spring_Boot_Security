package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService service;


    @Autowired
    public UserController(UserService service) {
        this.service = service;

    }
    @GetMapping("/user")
    public String getUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = service.findByUserName(email);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/registration")
    String createUserAccount(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String password) {

        service.create(name, email, password);
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String getAll(Model model) {
        model.addAttribute("users", service.getAll());
        return "getAll";
    }

    @GetMapping("/admin/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        User user = service.getById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/admin/update")
    public String update(@RequestParam("id") Long id,
                         @ModelAttribute("user") User user) {
        service.update(id, user);
        return "redirect:/admin";
    }


    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") Long id) {
        service.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addRole")
    public String addRole(@RequestParam Long id, @RequestParam String roleName) {
        service.addRole(id, roleName);
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteRole")
    public String deleteRole(@RequestParam Long id, @RequestParam String roleName) {
        service.deleteRole(id, roleName);
        return "redirect:/admin";
    }
}
