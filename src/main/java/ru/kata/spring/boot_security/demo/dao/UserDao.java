package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void saveUser(User user);

    List<User> getAll();

    void update(User user);

    void delete(User user);

    Optional<User> getById(Long id);

    User findByUserName(String email);

    Role findRole(String role);

    void saveRole(Role role);

    void deleteRole(Role role);
}
