package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void saveUser(User user);

    List<User> getAll();

    void delete(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
