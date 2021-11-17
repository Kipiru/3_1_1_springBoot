package web.dao;

import web.model.Role;


import java.util.List;

public interface RoleDao {

    List<Role> getAll();

    void create(Role role);

    Role readById(int id);

    void delete(Role role);

    void updateRole(Role role);

    Role getByName(String roles);
}
