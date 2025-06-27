package com.cafeteria_codequal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "pedido_ingredientes_base",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientesBase;

    @ManyToMany
    @JoinTable(
        name = "pedido_ingredientes_extras",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientesExtras;

    @ManyToOne
    @JoinColumn(name = "sabor_classico_id")
    private SaborClassico saborClassico;

    private double preco;

    public Pedido(List<Ingrediente> ingredientesBase, List<Ingrediente> ingredientesExtras, SaborClassico saborClassico, double preco) {
        this.ingredientesBase = ingredientesBase;
        this.ingredientesExtras = ingredientesExtras;
        this.saborClassico = saborClassico;
        this.preco = preco;
    }
}
