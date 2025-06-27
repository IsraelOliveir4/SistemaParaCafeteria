package com.cafeteria_codequal.dto;

import java.util.List;

public class PedidoRequest {

    private List<Long> ingredientesBaseIds;
    private List<Long> ingredientesExtrasIds;
    private Long saborClassicoId;
    private double preco;

    public PedidoRequest() {
    }

    public PedidoRequest(List<Long> ingredientesBaseIds, List<Long> ingredientesExtrasIds, Long saborClassicoId, double preco) {
        this.ingredientesBaseIds = ingredientesBaseIds;
        this.ingredientesExtrasIds = ingredientesExtrasIds;
        this.saborClassicoId = saborClassicoId;
        this.preco = preco;
    }

    public List<Long> getIngredientesBaseIds() {
        return ingredientesBaseIds;
    }

    public void setIngredientesBaseIds(List<Long> ingredientesBaseIds) {
        this.ingredientesBaseIds = ingredientesBaseIds;
    }

    public List<Long> getIngredientesExtrasIds() {
        return ingredientesExtrasIds;
    }

    public void setIngredientesExtrasIds(List<Long> ingredientesExtrasIds) {
        this.ingredientesExtrasIds = ingredientesExtrasIds;
    }

    public Long getSaborClassicoId() {
        return saborClassicoId;
    }

    public void setSaborClassicoId(Long saborClassicoId) {
        this.saborClassicoId = saborClassicoId;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
