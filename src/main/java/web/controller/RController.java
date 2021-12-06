package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class RController {
    private final UserService service;

    @Autowired
    public RController(UserService service) {
        this.service = service;
    }

    @GetMapping("/api/auth")
    public User auth(Authentication authentication) {
        return (User) service.loadUserByUsername(authentication.getName());
    }

    @GetMapping("/api/users")
    public List<User> adminPage() {
        return service.getAll();
    }

    @GetMapping("/api/{id}")
    public User read(@PathVariable("id") int id) {
        return service.readById(id);
    }

    @PostMapping("/api/add-user")
    public void addUser(@RequestBody User user) {
        service.create(user);
    }


    @PutMapping("/api/update-user")
    public void updateUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        service.update(user);
    }

    @DeleteMapping("/api/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(service.getUserById(id));
    }
}
