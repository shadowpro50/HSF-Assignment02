package com.se192491.assignment02.service;

import com.se192491.assignment02.pojo.Role;

import java.util.List;

public interface RoleService {
    public void save(Role role);
    public Role findById(int id);
    public List<Role> findAll();
    public Role update(int id,Role role);
    public void delete(int id);

}
