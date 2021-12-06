package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.User;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }



    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();

    }


    @Override
    @Transactional
    public void create(User user, String[] roles) {
        user.setRoles(roleService.getRoleSet(roles));
        userDao.create(user);
    }


    @Override
    @Transactional(readOnly = true)
    public User readById(int id) {
        return userDao.readById(id);
    }


    @Override
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, String[] roles) {
        User userFromDb = userDao.readById(user.getId());
        userFromDb.setName(user.getName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setAge(user.getAge());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setRoles(roleService.getRoleSet(roles));
        userDao.updateUser(userFromDb);
    }

    @Override
    @Transactional
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    @Transactional
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", s));
        }
        return user;
    }
}
