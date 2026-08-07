package com.example.demo.controller;

import com.example.demo.dto.CategoriaRequest;
import com.example.demo.dto.CategoriaResponse;
import com.example.demo.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Categoria", description = "CRUD operations for Categoria")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "List all Categoria records")
    @GetMapping
    public List<CategoriaResponse> findAll() {
        return categoriaService.findAll();
    }

    @Operation(summary = "Get a Categoria by id")
    @GetMapping("/{id}")
    public CategoriaResponse findById(@PathVariable Long id) {
        return categoriaService.findById(id);
    }

    @Operation(summary = "Create a new Categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse created = categoriaService.create(request);
        return ResponseEntity
            .created(URI.create("/api/categorias/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing Categoria")
    @PutMapping("/{id}")
    public CategoriaResponse update(@PathVariable Long id,
                                        @Valid @RequestBody CategoriaRequest request) {
        return categoriaService.update(id, request);
    }

    @Operation(summary = "Delete a Categoria by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoriaService.delete(id);
    }
}
