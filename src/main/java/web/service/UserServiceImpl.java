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
    public void create(User user) {
        userDao.save(user);
    }


//    @Override
//    @Transactional
//    public void create(User user, String[] roles) {
//        user.setRoles(roleService.getRoleSet(roles));
//        userDao.create(user);
//    }


    @Override
    @Transactional(readOnly = true)
    public User readById(int id) {
        return userDao.findById(id).get();
    }


    @Override
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    @Transactional
    public boolean updateUser(User user, Integer id) {
        Optional<User> userFromDb = userDao.findById(id);
        if (userFromDb.isPresent()) {
            userFromDb.get().setName(user.getName());
            userFromDb.get().setLastName(user.getLastName());
            userFromDb.get().setAge(user.getAge());
            userFromDb.get().setPassword(user.getPassword());
            userFromDb.get().setRoles(user.getRoles());
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
