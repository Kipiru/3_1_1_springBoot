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
        return roleDao.getAll();
    }

    @Override
    @Transactional
    public void create(Role role) {
        roleDao.create(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role readById(int id) {
        return roleDao.readById(id);
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
    public Set<Role> getRoleSet(String[] role){
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(roleDao.getByName(roles));
        }
        return roleSet;
    }


}
