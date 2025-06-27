package com.cafeteria_codequal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria_codequal.dto.PedidoRequest;
import com.cafeteria_codequal.entity.Ingrediente;
import com.cafeteria_codequal.entity.Pedido;
import com.cafeteria_codequal.entity.SaborClassico;
import com.cafeteria_codequal.repository.IngredienteRepository;
import com.cafeteria_codequal.repository.PedidoRepository;
import com.cafeteria_codequal.repository.SaborClassicoRepository;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final IngredienteRepository ingredienteRepository;
    private final SaborClassicoRepository saborClassicoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            IngredienteRepository ingredienteRepository,
                            SaborClassicoRepository saborClassicoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.ingredienteRepository = ingredienteRepository;
        this.saborClassicoRepository = saborClassicoRepository;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> realizarPedido(@RequestBody PedidoRequest pedidoRequest) {
        // Validar IDs base
        List<Ingrediente> ingredientesBase = ingredienteRepository.findAllById(pedidoRequest.getIngredientesBaseIds());
        if (ingredientesBase.size() != pedidoRequest.getIngredientesBaseIds().size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Um ou mais ingredientes base não existem.");
        }

        // Validar IDs extras
        List<Ingrediente> ingredientesExtras = ingredienteRepository.findAllById(pedidoRequest.getIngredientesExtrasIds());
        if (ingredientesExtras.size() != pedidoRequest.getIngredientesExtrasIds().size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Um ou mais ingredientes extras não existem.");
        }

        // Validar sabor clássico
        SaborClassico saborClassico = null;
        if (pedidoRequest.getSaborClassicoId() != null) {
            Optional<SaborClassico> saborOpt = saborClassicoRepository.findById(pedidoRequest.getSaborClassicoId());
            if (saborOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Sabor clássico informado não existe.");
            }
            saborClassico = saborOpt.get();
        }

        // Validar preço (exemplo simples: preço > 0)
        if (pedidoRequest.getPreco() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Preço inválido.");
        }

        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setIngredientesBase(ingredientesBase);
        pedido.setIngredientesExtras(ingredientesExtras);
        pedido.setSaborClassico(saborClassico);
        pedido.setPreco(pedidoRequest.getPreco());

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }
}
