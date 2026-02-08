package ru.kata.spring.boot_security.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDaoImpl roleDao;

    public RoleServiceImpl(RoleDaoImpl roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Set<Role> findAllById(List<Long> id) {
        return roleDao.findAllById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

//    @Override
//    @Transactional
//    public void saveRole(Role role) {
//        roleDao.saveRole(role);
//    }
//
//    @Override
//    @Transactional
//    public void deleteRole(Long id) {
//        Role role = findRole(id);
//        roleDao.deleteRole(role);
//    }

    @Override
    @Transactional(readOnly = true)
    public Role findRole(Long id) {
        return roleDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
    }
}
