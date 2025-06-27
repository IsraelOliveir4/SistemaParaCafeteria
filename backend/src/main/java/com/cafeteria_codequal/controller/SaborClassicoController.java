package com.cafeteria_codequal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria_codequal.entity.SaborClassico;
import com.cafeteria_codequal.repository.SaborClassicoRepository;

@RestController
@RequestMapping("/sabores")
@CrossOrigin(origins = "*")
public class SaborClassicoController {

    @PostMapping("/detectar")
    public SaborClassico detectar(@RequestBody List<Long> idsIngredientes) {
        return repository.findAll().stream()
            .filter(sabor -> {
                List<Long> idsSabor = sabor.getIngredientesBase()
                    .stream()
                    .map(i -> i.getId())
                    .sorted()
                    .toList();

                List<Long> idsRecebidos = idsIngredientes.stream().sorted().toList();

                return idsSabor.equals(idsRecebidos);
            })
            .findFirst()
            .orElse(null);
    }

    private final SaborClassicoRepository repository;

    public SaborClassicoController(SaborClassicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<SaborClassico> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public SaborClassico salvar(@RequestBody SaborClassico sabor) {
        return repository.save(sabor);
    }
}

