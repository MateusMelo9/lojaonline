package com.fbv.lojaonline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_compra")
public class ItemCompra {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Compra compra;
    private int quantidade;
    private double valorUnidade;
    private double valorTotal;
}
