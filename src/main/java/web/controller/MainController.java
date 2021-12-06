package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/")
public class MainController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping({"/admin", "/user"})
    public ModelAndView mainView(@AuthenticationPrincipal User curUser) {
        User currentUser = userService.getUserByName(curUser.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainView");
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
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
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") int id, @RequestBody User user) {
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

    @GetMapping("/login")
    public String errorLogin() {
        return "login";
    }

}