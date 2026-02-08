package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Component
public class RoleDaoImpl implements RoleDao {


    private final RoleRepository repository;

    public RoleDaoImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Role> findAllById(List<Long> id) {
        return repository.findByIdIn(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        repository.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        repository.delete(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }
}
