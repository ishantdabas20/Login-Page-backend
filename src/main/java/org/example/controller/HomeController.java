package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){

        return "Public Home";
    }

    @GetMapping("/user")
    public String user(){
        return "User Page";
    }


    @GetMapping("/admin")
    public String admin() {
        return "Admin Page";
    }
}
