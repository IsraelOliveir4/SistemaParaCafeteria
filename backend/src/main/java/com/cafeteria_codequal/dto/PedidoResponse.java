package com.cafeteria_codequal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoResponse {

    private String nomeCafe;
    private List<String> ingredientesBase;
    private List<String> ingredientesExtras;
    private double precoTotal;

}
