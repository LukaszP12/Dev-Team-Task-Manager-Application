package pl.piwowarski.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login-page")
    String accessLoginPage(){
        return "loginPage";
    }

}
