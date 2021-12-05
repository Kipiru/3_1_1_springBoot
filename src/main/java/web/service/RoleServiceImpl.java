package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return (List<Role>) roleDao.findAll();
    }

    @Override
    @Transactional
    public void create(Role role) {
        roleDao.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role readById(int id) {
        return roleDao.findById(id).get();
    }

    @Override
    @Transactional
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {

    }

    @Override
    @Transactional
    public Set<Role> getRoleSet(Set<Role> roles){
        Set<Role> roleSet = new HashSet<>();
        for (Role role : roles) {
            roleSet.add(roleDao.findRoleByRoleName(role.getRoleName()));
        }
        return roleSet;
    }


}
