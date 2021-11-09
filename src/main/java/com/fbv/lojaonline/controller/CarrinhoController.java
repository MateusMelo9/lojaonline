package com.fbv.lojaonline.controller;


import com.fbv.lojaonline.model.Cliente;
import com.fbv.lojaonline.model.Compra;
import com.fbv.lojaonline.model.ItemCompra;
import com.fbv.lojaonline.model.Produto;
import com.fbv.lojaonline.repository.ClienteRepository;
import com.fbv.lojaonline.repository.CompraRepository;
import com.fbv.lojaonline.repository.ItemCompraRepository;
import com.fbv.lojaonline.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCompraRepository itemCompraRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CompraRepository compraRepository;

    private Cliente cliente;
    private Compra compra = new Compra();

    private List<ItemCompra> itensCompra = new ArrayList<>();



    @GetMapping
    public ModelAndView inicio(){
        ModelAndView mv = new ModelAndView("carrinho/carrinho");
        mv.addObject("itens",itensCompra);
        return mv;
    }

    @RequestMapping("/add/{id}")
    public String adicionarItem(@PathVariable long id){
        Produto produto = produtoRepository.findById(id).get();
        ItemCompra item = new ItemCompra();
        item.setProduto(produto);
        item.setQuantidade(item.getQuantidade()+1);
        item.setValorUnidade(produto.getValorVenda());
        item.setValorTotal(item.getValorUnidade() * item.getQuantidade());
        compra.setValorTotal(compra.getValorTotal()+item.getValorTotal());
        itensCompra.add(item);
        return "redirect:/carrinho";
    }

    @RequestMapping("/comprar")
    public ModelAndView comprar(){
        ModelAndView mv = new ModelAndView("carrinho/comprar");
        buscarUsuarioLogado();
        mv.addObject("cliente",cliente);
        mv.addObject("itens",itensCompra);
        mv.addObject("compra",compra);
        return mv;
    }

    @PostMapping("/finalizar")
    public ModelAndView confirmarCompra(){
        ModelAndView mv = new ModelAndView("cliente/mensagemfinalizou");
        compra.setCliente(cliente);
        compra.setDataCompra(new Date());
        compraRepository.saveAndFlush(compra);
        for (ItemCompra i : itensCompra){
            i.setCompra(compra);
            itemCompraRepository.saveAndFlush(i);
        }
        itensCompra = new ArrayList<>();
        compra = new Compra();
        return mv;
    }

    @RequestMapping("/remover/{id}")
    public String removerCarrinho(@PathVariable long id){
        removerItem(id);
        return "redirect:/carrinho";
    }

    @RequestMapping("/comprar/remover/{id}")
    public String removerCompra(@PathVariable long id){
        removerItem(id);
        return "redirect:/carrinho/comprar";
    }

    public void removerItem(long id){
        for (ItemCompra i : itensCompra){
            if(i.getProduto().getId()==id){
                itensCompra.remove(i);
                break;
            }
        }
    }

    public void buscarUsuarioLogado(){
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        cliente = new Cliente();
        if(!(autenticado instanceof AnonymousAuthenticationToken)){
            String email = autenticado.getName();
            cliente = clienteRepository.findByEmail(email);
        }
    }

}
