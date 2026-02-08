package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(User user, List<Long> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> allById = roleService.findAllById(roleIds);
        user.setRoles(allById);
        userDao.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void update(Long id, User updateUser, List<Long> roleIds) {
        User userById = findById(id);
        userById.setName(updateUser.getName());
        userById.setEmail(updateUser.getEmail());
        Set<Role> roles = roleIds.stream().map(roleService::findRole).collect(Collectors.toSet());
        userById.setRoles(roles);
        userDao.saveUser(userById);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User userById = findById(id);
        userDao.delete(userById);
    }

    // для userDetailsServiceImpl
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Not found"));
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));
    }
}
