package com.cafeteria_codequal.service;

import com.cafeteria_codequal.dto.PedidoRequest;
import com.cafeteria_codequal.dto.PedidoResponse;
import com.cafeteria_codequal.entity.Ingrediente;
import com.cafeteria_codequal.entity.Pedido;
import com.cafeteria_codequal.entity.SaborClassico;
import com.cafeteria_codequal.repository.IngredienteRepository;
import com.cafeteria_codequal.repository.PedidoRepository;
import com.cafeteria_codequal.repository.SaborClassicoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private IngredienteRepository ingredienteRepo;
    private SaborClassicoRepository saborRepo;
    private PedidoRepository pedidoRepo;
    private PedidoService pedidoService;

    @BeforeEach
    void setup() {
        ingredienteRepo = mock(IngredienteRepository.class);
        saborRepo = mock(SaborClassicoRepository.class);
        pedidoRepo = mock(PedidoRepository.class);
        pedidoService = new PedidoService(ingredienteRepo, saborRepo, pedidoRepo);
    }

    @Test
    void deveProcessarPedidoPersonalizadoComSucesso() {
        // Ingredientes simulados
        Ingrediente cafe = new Ingrediente(1L, "Café", "BASE", 5.0, 10);
        Ingrediente leite = new Ingrediente(2L, "Leite", "EXTRA", 2.0, 10);

        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of(1L));
        request.setIngredientesExtrasIds(List.of(2L));
        request.setSaborClassicoId(null);
        request.setPreco(7.0);

        // Mock dos ingredientes
        when(ingredienteRepo.findAllById(List.of(1L))).thenReturn(List.of(cafe));
        when(ingredienteRepo.findAllById(List.of(2L))).thenReturn(List.of(leite));
        when(saborRepo.findAll()).thenReturn(List.of());
        when(pedidoRepo.save(any(Pedido.class))).thenAnswer(i -> i.getArgument(0));

        PedidoResponse response = pedidoService.processarPedido(request);

        assertEquals("Personalizado", response.getNomeCafe());
        assertEquals(List.of("Café"), response.getIngredientesBase());
        assertEquals(List.of("Leite"), response.getIngredientesExtras());
        assertEquals(7.0, response.getPrecoTotal());
    }

    @Test
    void deveLancarExcecaoSeNaoHouverIngredienteBase() {
        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of()); // vazio
        request.setIngredientesExtrasIds(List.of(2L));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.processarPedido(request);
        });

        assertEquals("Selecione ao menos um ingrediente base.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeMaisDeDoisExtrasSelecionados() {
        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of(1L));
        request.setIngredientesExtrasIds(List.of(2L, 3L, 4L)); // mais de dois extras

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.processarPedido(request);
        });

        assertEquals("No máximo 2 ingredientes extras permitidos.", exception.getMessage());
    }

    @Test
    void deveRetornarFalseSeIngredienteBaseNaoExistir() {
        when(ingredienteRepo.existsById(1L)).thenReturn(false);

        boolean valido = pedidoService.validarPedido(List.of(1L), List.of());

        assertFalse(valido);
    }

    @Test
    void deveIdentificarSaborClassicoComSucesso() {
        // Ingredientes simulados
        Ingrediente base1 = new Ingrediente(1L, "Café", "BASE", 5.0, 10);
        Ingrediente base2 = new Ingrediente(2L, "Leite", "BASE", 3.0, 10);
        Ingrediente extra = new Ingrediente(3L, "Canela", "EXTRA", 1.0, 10);

        List<Ingrediente> baseIngredientes = List.of(base1, base2);

        // Sabor clássico que bate com os ingredientes base
        SaborClassico sabor = new SaborClassico(1L, "Latte", baseIngredientes, 9.0);

        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of(1L, 2L));
        request.setIngredientesExtrasIds(List.of(3L));
        request.setSaborClassicoId(null);

        // Mocks
        when(ingredienteRepo.findAllById(List.of(1L, 2L))).thenReturn(baseIngredientes);
        when(ingredienteRepo.findAllById(List.of(3L))).thenReturn(List.of(extra));
        when(saborRepo.findAll()).thenReturn(List.of(sabor));
        when(pedidoRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Execução
        PedidoResponse response = pedidoService.processarPedido(request);

        // Verificação
        assertEquals("Latte", response.getNomeCafe());
        assertEquals(10.0, response.getPrecoTotal()); // 9 do sabor + 1 do extra
    }

    @Test
    void deveLancarExcecaoQuandoIngredienteBaseNaoExiste() {
        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of(99L)); // ID inexistente
        request.setIngredientesExtrasIds(List.of());

        when(ingredienteRepo.existsById(99L)).thenReturn(false);

        boolean valido = pedidoService.validarPedido(request.getIngredientesBaseIds(), request.getIngredientesExtrasIds());

        assertFalse(valido, "O pedido não deve ser válido com ingrediente inexistente");
    }

    @Test
    void deveLancarExcecaoQuandoMaisDeDoisExtrasForemEnviados() {
        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of(1L));
        request.setIngredientesExtrasIds(List.of(2L, 3L, 4L)); // 3 extras

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.processarPedido(request);
        });

        assertEquals("No máximo 2 ingredientes extras permitidos.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNaoTiverIngredienteBase() {
        PedidoRequest request = new PedidoRequest();
        request.setIngredientesBaseIds(List.of()); // vazio
        request.setIngredientesExtrasIds(List.of(2L));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.processarPedido(request);
        });

        assertEquals("Selecione ao menos um ingrediente base.", ex.getMessage());
    }

}
