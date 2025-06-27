package com.cafeteria_codequal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafeteria_codequal.dto.PedidoRequest;
import com.cafeteria_codequal.dto.PedidoResponse;
import com.cafeteria_codequal.entity.Ingrediente;
import com.cafeteria_codequal.entity.Pedido;
import com.cafeteria_codequal.entity.SaborClassico;
import com.cafeteria_codequal.repository.IngredienteRepository;
import com.cafeteria_codequal.repository.PedidoRepository;
import com.cafeteria_codequal.repository.SaborClassicoRepository;


@Service
public class PedidoService {

    @Autowired
    private IngredienteRepository ingredienteRepo;

    @Autowired
    private SaborClassicoRepository saborRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    public PedidoResponse processarPedido(PedidoRequest request) {
        if (request.getIngredientesBaseIds().isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um ingrediente base.");
        }

        if (request.getIngredientesExtrasIds().size() > 2) {
            throw new IllegalArgumentException("No máximo 2 ingredientes extras permitidos.");
        }

        List<Ingrediente> base = ingredienteRepo.findAllById(request.getIngredientesBaseIds());
        List<Ingrediente> extras = ingredienteRepo.findAllById(request.getIngredientesExtrasIds());

        SaborClassico sabor = saborRepo.findAll().stream()
            .filter(s -> base.containsAll(s.getIngredientesBase()) && s.getIngredientesBase().containsAll(base))
            .findFirst().orElse(null);

        double precoBase = base.stream().mapToDouble(Ingrediente::getPreco).sum();
        double precoExtras = extras.stream().mapToDouble(Ingrediente::getPreco).sum();
        double precoTotal = sabor != null ? sabor.getPreco() + precoExtras : precoBase + precoExtras;

        Pedido pedido = new Pedido(base, extras, sabor, precoTotal);
        pedidoRepo.save(pedido);

        return new PedidoResponse(
            sabor != null ? sabor.getNome() : "Personalizado",
            base.stream().map(Ingrediente::getNome).toList(),
            extras.stream().map(Ingrediente::getNome).toList(),
            precoTotal
        );
        }

        public boolean validarPedido(List<Long> ingredientesBaseIds, List<Long> ingredientesExtrasIds) {
        // Verifica se todos os ingredientes base existem
        for (Long id : ingredientesBaseIds) {
            if (!ingredienteRepo.existsById(id)) {
                return false;
                }
            }
            return true;
        }

        public double calcularPreco(List<Long> ingredientesBaseIds, List<Long> ingredientesExtrasIds, Long saborClassicoId) {
        double preco = 0;

        if (saborClassicoId != null) {
            SaborClassico sabor = saborRepo.findById(saborClassicoId).orElse(null);
            if (sabor != null) {
                preco += sabor.getPreco();
            }
        } else {
            List<Ingrediente> bases = ingredienteRepo.findAllById(ingredientesBaseIds);
            for (Ingrediente base : bases) {
                preco += base.getPreco();
            }
        }

        List<Ingrediente> extras = ingredienteRepo.findAllById(ingredientesExtrasIds);
        for (Ingrediente extra : extras) {
            preco += extra.getPreco();
        }

        return preco;
    }

    public PedidoService(IngredienteRepository ingredienteRepo, SaborClassicoRepository saborRepo, PedidoRepository pedidoRepo) {
        this.ingredienteRepo = ingredienteRepo;
        this.saborRepo = saborRepo;
        this.pedidoRepo = pedidoRepo;
    }

}
