package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User create(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Role role = userDao.findRole("ADMIN");
        if (role == null) {
            role = new Role("ADMIN");
            userDao.saveRole(role);
        }
        user.getRoles().add(role);
        return userDao.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void update(Long id, User updateUser) {
        User userById = getById(id);
        userById.setName(updateUser.getName());
        userById.setEmail(updateUser.getEmail());
        userDao.update(userById);
    }

    @Override
    @Transactional
    public void addRole(Long id, String roleName) {
        User userById = getById(id);
        Role findRole = userDao.findRole(roleName);
        if (findRole == null) {
            findRole = new Role(roleName);
            userDao.saveRole(findRole);
        } else {
            boolean contains = userById.getRoles().contains(findRole);
            if (contains) {
                return;
            }
        }
        userById.getRoles().add(findRole);
        userDao.update(userById);
    }

    @Override
    @Transactional
    public void deleteRole(Long id, String name) {
        User userById = userDao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));
        Role role = userDao.findRole(name);
        userById.getRoles().remove(role);
        userDao.update(userById);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User userById = getById(id);
        userDao.delete(userById);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userDao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        if (username == null) {
            throw new EntityNotFoundException();
        }
        return userDao.findByUserName(username);
    }
}
