package pl.piwowarski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.piwowarski.model.User;
import pl.piwowarski.service.UserService;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration/create-new-user")
    public String createNewUser(Model model) {
        User newUser = new User();

        model.addAttribute("newUser", newUser);
        return "newUser";
    }

    @PostMapping("/registration/create-user")
    public String createNewUser(@Valid @ModelAttribute(value = "newUser") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "newUser";
        } else {
            userService.createUser(user);
            return "redirect:/users";
        }
    }

    @GetMapping("/registration/remove-user/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
