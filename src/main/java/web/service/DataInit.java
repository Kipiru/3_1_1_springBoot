package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class DataInit {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        Set<Role> adminSet = new HashSet<>();
        adminSet.add(adminRole);
        roleService.create(adminRole);
        User admin = new User("ADMIN", "ADMIN", 10, "$2a$12$6Vpq4WUwqhryMWhn8Ppx9uzcqNOOeJKf8YJM6tk68jomoyq3lHGH6",adminSet);
        userService.create(admin);

        Role userRole = new Role("ROLE_USER");
        Set<Role> userSet = new HashSet<>();
        userSet.add(userRole);
        roleService.create(userRole);
        User user = new User("USER", "USER", 20, "$2a$12$6524/dILxrxSCmRpvM3pq.RomUwgABczQJmTiD2GYp6Xj/FfxwrvK", userSet);
        userService.create(user);

        Set<Role> bothRoles = new HashSet<>();
        bothRoles.add(adminRole);
        bothRoles.add(userRole);
        User adminUser = new User("adminuser", "adminuser", 30, "$2a$12$fxKn3w9YaK3NB0HUylct3OGvg8gb5.1IzTbEHwjCaHosadlSlK8ra\n", bothRoles);
        userService.create(adminUser);
    }
}
