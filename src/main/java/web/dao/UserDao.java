package web.dao;


import web.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    void create(User user);

    User readById(int id);

    void delete(User user);

    void updateUser(User user);

    User getUserByName(String name);
}
