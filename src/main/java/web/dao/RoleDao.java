package web.dao;

import org.springframework.data.repository.CrudRepository;
import web.model.Role;
import web.model.User;


import java.util.List;

public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findRoleByRole(String role);

//    List<Role> getAll();
//
//    void create(Role role);
//
//    Role readById(int id);
//
//    void delete(Role role);
//
//    void updateRole(Role role);
//
//    Role getByName(String roles);
}
