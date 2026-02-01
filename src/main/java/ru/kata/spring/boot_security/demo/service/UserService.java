package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


public interface UserService {

    User create(String name, String email, String password);

    List<User> getAll();

    void update(Long id, User updateUser);

    void delete(Long id);

    User getById(Long id);

    User findByUserName(String username);

    void addRole(Long id, String role);

    void deleteRole(Long id, String name);
}
