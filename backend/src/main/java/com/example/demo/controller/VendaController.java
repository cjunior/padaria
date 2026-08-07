package com.example.demo.controller;

import com.example.demo.dto.VendaRequest;
import com.example.demo.dto.VendaResponse;
import com.example.demo.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Venda", description = "CRUD operations for Venda")
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @Operation(summary = "List all Venda records")
    @GetMapping
    public List<VendaResponse> findAll() {
        return vendaService.findAll();
    }

    @Operation(summary = "Get a Venda by id")
    @GetMapping("/{id}")
    public VendaResponse findById(@PathVariable Long id) {
        return vendaService.findById(id);
    }

    @Operation(summary = "Create a new Venda")
    @PostMapping
    public ResponseEntity<VendaResponse> create(@Valid @RequestBody VendaRequest request) {
        VendaResponse created = vendaService.create(request);
        return ResponseEntity
            .created(URI.create("/api/vendas/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing Venda")
    @PutMapping("/{id}")
    public VendaResponse update(@PathVariable Long id,
                                        @Valid @RequestBody VendaRequest request) {
        return vendaService.update(id, request);
    }

    @Operation(summary = "Delete a Venda by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vendaService.delete(id);
    }
}
