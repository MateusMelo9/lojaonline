package com.fbv.lojaonline.config;

import com.fbv.lojaonline.model.Cliente;
import com.fbv.lojaonline.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Cliente cliente = repository.findByEmail(s);
        if(cliente == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return cliente;
    }
}
