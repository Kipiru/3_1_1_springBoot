package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/")
public class RController {


    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RController(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/auth")
    public User auth(@AuthenticationPrincipal User user) {
        return (User) userService.loadUserByUsername(user.getName());
    }

    @GetMapping("/admin/users")
    public List<User> allUsers() {
        return userService.getAll();
    }

    @GetMapping("/admin/roles")
    public List<Role> allRoles() {
        return roleService.getAll();
    }

    @GetMapping("/admin/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.readById(id);
    }

    @PostMapping("/admin/save")
    public ResponseEntity<?> create(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") int id, @RequestBody User user) {
        User oldUser = userService.readById(id);
        if(!user.getPassword().equals(oldUser.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        boolean updated = userService.updateUser(user, id);
        return updated ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUserForm(@PathVariable(value = "id") int id) {
        boolean deleted = userService.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}