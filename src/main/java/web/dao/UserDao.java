package web.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findUserByName(String name);
}
