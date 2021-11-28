package web.service;

import web.model.Role;


import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAll();

    void create(Role role);

    Role readById(int id);

    void delete(Role role);

    void updateRole(Role role);


    Set<Role> getRoleSet(String[] role);
}
