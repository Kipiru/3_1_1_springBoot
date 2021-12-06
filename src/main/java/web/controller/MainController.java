package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserService userService;


    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/login")
    public String errorLogin() {
        return "login";
    }
}
