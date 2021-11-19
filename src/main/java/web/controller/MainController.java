package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import java.util.List;


@Controller
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

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal User user, Model model) {
        User currentUser = userService.getUserByName(user.getName());
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", currentUser);
        model.addAttribute("roles", roles);
        return "user";
    }

    @GetMapping("/admin/users")
    public ModelAndView allUsers() {
        List<User> userList = userService.getAll();
        List<Role> roles = roleService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }


    @GetMapping("/admin/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAll());
        return "new_user";
    }

    @PostMapping("/admin/save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("addRoles") String[] roles) {
        userService.create(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUserForm(@PathVariable int id, Model model) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAll());
        return "edit";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("addRoles") String[] roles) {
        userService.updateUser(user, roles);
        return "redirect:/admin/users";
    }

    @RequestMapping("/admin/delete/{id}")
    public String deleteUserForm(@PathVariable int id) {
        userService.delete(userService.readById(id));
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String errorLogin() {
        return "login";
    }

}