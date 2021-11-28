package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.User;
import java.util.List;

public interface UserService {

    List<User> getAll();

    void create(User user);


    User readById(int id);

    void delete(User user);

    boolean updateUser(User user, Integer id);

    User getUserByName(String name);

    UserDetails loadUserByUsername(String name);

}
