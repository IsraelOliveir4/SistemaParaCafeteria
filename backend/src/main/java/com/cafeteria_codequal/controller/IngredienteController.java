package com.cafeteria_codequal.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cafeteria_codequal.entity.Ingrediente;
import com.cafeteria_codequal.repository.IngredienteRepository;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin(origins = "*") // libera para o front acessar
public class IngredienteController {

    private final IngredienteRepository repository;

    public IngredienteController(IngredienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ingrediente> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Ingrediente salvar(@RequestBody Ingrediente ingrediente) {
        return repository.save(ingrediente);
    }
}
