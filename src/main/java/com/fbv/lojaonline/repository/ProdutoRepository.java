package com.fbv.lojaonline.repository;

import com.fbv.lojaonline.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    
}
