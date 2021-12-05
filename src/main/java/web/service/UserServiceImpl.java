package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleService roleService;


    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;

        this.roleService = roleService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return (List<User>) userDao.findAll();

    }

    @Override
    @Transactional
    public boolean create(User user) {
        userDao.save(user);
        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public User readById(int id) {
        if (userDao.findById(id).isPresent()) {
            return userDao.findById(id).get();
        }
        return null;
    }


    @Override
    @Transactional
    public boolean delete(int id) {
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {
            userDao.delete(user.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateUser(User user, int id) {
        Optional<User> userFromDb = userDao.findById(id);
        if (userFromDb.isPresent()) {
            userFromDb.get().setName(user.getName());
            userFromDb.get().setLastName(user.getLastName());
            userFromDb.get().setAge(user.getAge());
            userFromDb.get().setPassword(user.getPassword());
            userFromDb.get().setRoles(roleService.getRoleSet(user.getRoles()));
//            userFromDb.get().setRoles(user.getRoles());
//            userFromDb.get().setRoles(roleService.getRoleSet(user.getRolesFromStringArray()));
            userDao.save(userFromDb.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public User getUserByName(String name) {
        return userDao.findUserByName(name);
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    @Transactional
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", s));
        }
        return user;
    }
}
