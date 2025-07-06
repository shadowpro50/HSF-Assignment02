package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Role;
import com.se192491.assignment02.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role update(int id, Role role) {
        Role oldRole = findById(id);
        if (oldRole == null) {
            return null;
        }
        oldRole.setRoleName(role.getRoleName());
        return roleRepository.save(oldRole);
    }

    @Override
    public void delete(int id) {
        roleRepository.deleteById(id);
    }
}
