package com.fbv.lojaonline.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fbv.lojaonline.model.ItemCompra;
import com.fbv.lojaonline.model.Produto;
import com.fbv.lojaonline.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private static String caminhoImagem = "C:\\Users\\mateus.melo.veloso\\IdeaProjects\\imagens\\";

    @Autowired
    private ProdutoRepository repository;

    private List<ItemCompra> itensCompra = new ArrayList<>();

    @GetMapping
    public ModelAndView inicio(){
        ModelAndView mv = new ModelAndView("produto/produtos");
        mv.addObject("produtos", repository.findAll());
        return mv;
    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String novo(Produto produto){
        return "produto/cadastro-produto";
    }
    
    @RequestMapping(value = "/novo", method = RequestMethod.POST)
    public String cadastrar(Produto produto, @RequestParam("file") MultipartFile arquivo){

        gravarImagem(arquivo,produto);
        repository.save(produto);

        return "redirect:novo";
    }
    
    @RequestMapping(value = "/consulta", method = RequestMethod.GET)
    public ModelAndView consulta(){
        ModelAndView mv = new ModelAndView("produto/consulta-produto");
        mv.addObject("produtos", repository.findAll());
        return mv;
        
    }

    @RequestMapping(value = "/detalhe/{id}", method = RequestMethod.GET)
    public ModelAndView detalhe(@PathVariable long id){
        ModelAndView mv = new ModelAndView("produto/detalhe-produto");
        mv.addObject("produto", repository.findById(id).get());
        return mv;
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable long id){
        ModelAndView mv = new ModelAndView("produto/cadastro-produto");
        mv.addObject("produto",repository.findById(id).get());
        return mv;
    }

    @RequestMapping(value = "/deleta/{id}", method = RequestMethod.GET)
    public String deleta(@PathVariable long id){
        repository.deleteById(id);
        return "redirect:/produto/consulta";
    }

    @RequestMapping(value = "/mostrarImagem/{ìmagem}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] mostrarImagem(@PathVariable("ìmagem") String imagem){
        File imagemArquivo = new File(caminhoImagem+imagem);
        if(imagemArquivo!=null || imagem.trim().length()>0){
            try {
                return Files.readAllBytes(imagemArquivo.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void gravarImagem(MultipartFile arquivo,Produto produto){
        try {
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path camiho = Paths.get(caminhoImagem+arquivo.getOriginalFilename());
                Files.write(camiho,bytes);
                produto.setNomeImagem(arquivo.getOriginalFilename());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
