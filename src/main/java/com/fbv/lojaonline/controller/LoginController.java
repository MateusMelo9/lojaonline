package com.fbv.lojaonline.controller;

import com.fbv.lojaonline.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    private ClienteRepository repo;
    
    @GetMapping("/login")
    public String index(){
        return "login";
    }
}
