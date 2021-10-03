package com.fbv.lojaonline.controller;

import com.fbv.lojaonline.model.Cliente;
import com.fbv.lojaonline.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String novo(Cliente cliente){
        return "cliente/cadastro-cliente";
    }

    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public String cadastrar(Cliente cliente){
        repository.save(cliente);
        return "redirect:/login";
    }

    @RequestMapping(value = "/consulta", method = RequestMethod.GET)
    public ModelAndView consulta(){
        ModelAndView mv = new ModelAndView("cliente/consulta-cliente");
        mv.addObject("clientes",repository.findAll());
        return mv;
    }

    @RequestMapping(value = "/deleta/{id}", method = RequestMethod.GET)
    public String deleta(@PathVariable long id){
        repository.deleteById(id);
        return "redirect:/cliente/consulta";
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable long id){
        ModelAndView mv = new ModelAndView("cliente/cadastro-cliente");
        mv.addObject("cliente",repository.findById(id));
        return mv;
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.POST)
    public String editar(Cliente cliente){
        repository.save(cliente);
        return "redirect:/cliente/consulta";
    }

}
