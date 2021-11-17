package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;

import javax.annotation.PostConstruct;


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
        roleService.create(adminRole);
        User admin = new User("ADMIN", "ADMIN", 1, "ADMIN");
        userService.create(admin, new String[]{adminRole.getRole()});

        Role userRole = new Role("ROLE_USER");
        roleService.create(userRole);
        User user = new User("USER", "USER", 1, "USER");
        userService.create(user, new String[]{userRole.getRole()});

        User adminUser = new User("adminuser", "adminuser", 1, "adminuser");
        userService.create(adminUser, new String[]{adminRole.getRole(), userRole.getRole()});
    }
}
