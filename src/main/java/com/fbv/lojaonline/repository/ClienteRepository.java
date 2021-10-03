package com.fbv.lojaonline.repository;

import com.fbv.lojaonline.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
