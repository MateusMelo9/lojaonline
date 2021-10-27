package com.fbv.lojaonline.controller;

import com.fbv.lojaonline.model.Cliente;
import com.fbv.lojaonline.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private ClienteRepository repo;
    
    @GetMapping("/login")
    public String index(){
        return "login";
    }

    @PostMapping("/logar")
    public String logar(Model model, Cliente cliParam){
        Cliente cli = this.repo.login(cliParam.getEmail(),cliParam.getSenha()); 
        if(cli != null){
            return "redirect:/";
        }
        return "login";
    }
}
