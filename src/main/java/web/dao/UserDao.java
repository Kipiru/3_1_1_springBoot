package web.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import web.model.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findUserByName(String name);
//
//    List<User> getAll();
//
//    void create(User user);
//
//    User readById(int id);
//
//    void delete(User user);
//
//    void updateUser(User user);
//
//    User getUserByName(String name);
}
