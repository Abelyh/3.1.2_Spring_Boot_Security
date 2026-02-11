package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;
    private final RoleService roleService;

    public AdminController(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String showForm() {
        return "create";
    }

    @PostMapping("/create")
    String createUserAccount(@ModelAttribute("user") User user,
                             @RequestParam("roleIds") List<Long> roleIds) {
        service.create(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", service.getAll());
        return "getAll";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        User user = service.findById(id);
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") Long id,
                         @ModelAttribute("user") User user,  @RequestParam("roleIds") List<Long> roleIds) {
        service.update(id, user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        service.delete(id);
        return "redirect:/admin";
    }


}
