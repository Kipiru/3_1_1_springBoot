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

    @GetMapping("/admin")
    public ModelAndView allUsers() {
        List<User> userList = userService.getAll();
        List<Role> roles = roleService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("roles", roles);

        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.addObject("allRoles", roleService.getAll());
        return modelAndView;
    }


    @PostMapping("/admin/save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("roles") String[] roles) {
        userService.create(user, roles);
        return "redirect:/admin";
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