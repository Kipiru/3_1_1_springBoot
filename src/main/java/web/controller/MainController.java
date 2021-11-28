package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

//    @GetMapping({"/admin", "/user"})
//    public ModelAndView allUsers(@AuthenticationPrincipal User curUser) {
//        User currentUser = userService.getUserByName(curUser.getName());
//        List<User> userList = userService.getAll();
//        List<Role> roles = roleService.getAll();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("admin");
//        modelAndView.addObject("userList", userList);
//        modelAndView.addObject("roles", roles);
//        modelAndView.addObject("currentUser", currentUser);
//        User user = new User();
//        modelAndView.addObject("user", user);
//        return modelAndView;
//    }


    @GetMapping("/admin")
    public ResponseEntity<List<User>> allUsers() {

        List<User> userList = userService.getAll();

        return userList != null && !userList.isEmpty()
                ? new ResponseEntity<>(userList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("/admin/save")
//    public String create(@ModelAttribute("user") User user,
//                         @RequestParam("roles") String[] roles) {
//        userService.create(user, roles);
//        return "redirect:/admin";
//    }

    @PostMapping("/admin/save")
    public ResponseEntity<?> create(@RequestBody User user
//            ,
//                                 @RequestBody String[] roles
    ) {
//        userService.create(user, roles);
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


//    @PostMapping("/admin/update/{id}")
//    public String updateUser(User user, String[] roles) {
//        userService.updateUser(user, roles);
//        return "redirect:/admin";
//    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") int id, @RequestBody User user) {
        boolean updated = userService.updateUser(user, id);
        return updated ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>("Wrong user ID", HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUserForm(@PathVariable(value = "id") int id) {
        boolean deleted = userService.delete(userService.readById(id));
        return deleted ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>("Wrong user ID", HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/login")
    public String errorLogin() {
        return "login";
    }

}