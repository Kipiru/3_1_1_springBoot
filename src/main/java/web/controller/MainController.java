package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
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

//    @GetMapping("/user")
//    public String user(@AuthenticationPrincipal User curUser, Model model) {
//        User currentUser = userService.getUserByName(curUser.getName());
////        List<Role> roles = roleService.getAll();
//        model.addAttribute("currentUser", currentUser);
////        model.addAttribute("roles", roles);
//        return "admin";
//    }

    @GetMapping({"/admin", "/user"})
    public ModelAndView allUsers(
         @AuthenticationPrincipal User curUser
//           Authentication auth
    ) {
//        UserDetails currentUser = userService.loadUserByUsername(auth.getName());
               User currentUser = userService.getUserByName(curUser.getName());
        List<User> userList = userService.getAll();
        List<Role> roles = roleService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("currentUser", currentUser);

        User user = new User();
        modelAndView.addObject("user", user);
        // modelAndView.addObject("allRoles", roleService.getAll());
        return modelAndView;
    }


    @PostMapping("/admin/save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("roles") String[] roles) {
        userService.create(user, roles);
        return "redirect:/admin";
    }

//    @GetMapping("/admin/edit/{id}")
//    public String editUserForm(@PathVariable int id, Model model) {
//        User editUser = userService.readById(id);
//        model.addAttribute("editUser", editUser);
//        model.addAttribute("editRoles", editUser.getRoles());
//        return "edit";
//    }

//    @PostMapping("/admin/update/{id}")
//    public String updateUser(@ModelAttribute("editUser") User user,
//                             @RequestParam("editRoles") String[] roles) {
//        userService.updateUser(user, roles);
//        return "redirect:/admin";
//    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(User user, String[] roles) {
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/delete/{id}")
    public String deleteUserForm(@PathVariable int id) {
        userService.delete(userService.readById(id));
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String errorLogin() {
        return "login";
    }

}