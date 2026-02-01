package ru.kata.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public User saveUser(User user) {
        manager.persist(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return manager.createQuery("From User", User.class).getResultList();
    }


    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(manager.find(User.class, id));
    }

    @Override
    public void update(User userById) {
        manager.merge(userById);
    }

    @Override
    public void delete(User user) {
        manager.remove(user);
    }

    @Override
    public void saveRole(Role role) {
        manager.persist(role);
    }

    @Override
    public User findByUserName(String email) {
        String jpql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email";
        return manager.createQuery(jpql, User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public Role findRole(String roleName) {
        try {
            return manager.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :roleName",
                            Role.class
                    )
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteRole(Role role) {
        manager.remove(role);
    }
}
