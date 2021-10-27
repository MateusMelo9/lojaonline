package com.fbv.lojaonline.repository;

import com.fbv.lojaonline.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    @Query(value="select * from cliente where email = :email and senha = :senha", nativeQuery = true)
    public Cliente login(String email, String senha);
}
