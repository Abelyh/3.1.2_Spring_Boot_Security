package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleDao {

    Set<Role> findAllById(List<Long> id);
     List<Role> findAll();

    void saveRole(Role role);

    void deleteRole(Role role);

    Optional<Role> findById(Long id);
}
