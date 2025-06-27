package com.cafeteria_codequal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaborClassico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double preco;

    @ManyToMany
    @JoinTable(
        name = "sabor_ingredientes",
        joinColumns = @JoinColumn(name = "sabor_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientesBase;

    public SaborClassico(Long id, String nome, List<Ingrediente> ingredientesBase, double preco) {
    this.id = id;
    this.nome = nome;
    this.ingredientesBase = ingredientesBase;
    this.preco = preco;
    }
}

