package web.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import web.model.User;


@EnableJpaRepositories
public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByName(String name);
}
