package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.User;
import java.util.List;

public interface UserService {

    List<User> getAll();

    boolean create(User user);


    User readById(int id);

    boolean delete(int id);

    boolean updateUser(User user, int id);

    User getUserByName(String name);

    UserDetails loadUserByUsername(String name);

}
