package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import web.model.Role;


@EnableJpaRepositories
public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findRoleByRoleName(String role);
}
