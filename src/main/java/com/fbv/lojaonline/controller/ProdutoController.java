package com.fbv.lojaonline.controller;


import java.util.Optional;

import com.fbv.lojaonline.model.Produto;
import com.fbv.lojaonline.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String novo(Produto produto){
        return "produto/cadastro-produto";
    }
    
    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public String cadastrar(Produto produto){
        repository.save(produto);
        return "redirect:novo";
    }
    
    @RequestMapping(value = "/consulta", method = RequestMethod.GET)
    public ModelAndView consulta(){
        ModelAndView mv = new ModelAndView("produto/consulta-produto");
        mv.addObject("produtos", repository.findAll());
        return mv;
        
    }

   /*
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable long id){
        ModelAndView mv = new ModelAndView("produto/cadastro-produto");
        mv.addObject("produto",repository.findById(id));
        return mv;
    }
   
    @RequestMapping(value = "/editar", method = RequestMethod.GET)
    public ModelAndView editar(){
        ModelAndView mv = new ModelAndView("produto/cadastro-produto");
        mv.addObject("produto",repository.findById(4L));
        return mv;
    }
    */
    @RequestMapping(value = "/deleta/{id}", method = RequestMethod.GET)
    public String deleta(@PathVariable long id){
        repository.deleteById(id);
        return "redirect:/produto/consulta";
    }



}
