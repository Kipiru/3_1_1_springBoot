package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.User;
import java.util.List;

public interface UserService {

    List<User> getAll();

    void create(User user, String[] roles);

    User readById(int id);

    void delete(User user);

    void updateUser(User user, String[] roles);

    User getUserByName(String name);

    UserDetails loadUserByUsername(String name);

}
