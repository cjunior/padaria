package com.example.demo.controller;

import com.example.demo.dto.ItemVendaRequest;
import com.example.demo.dto.ItemVendaResponse;
import com.example.demo.service.ItemVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "ItemVenda", description = "CRUD operations for ItemVenda")
@RestController
@RequestMapping("/api/itemVendas")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    @Operation(summary = "List all ItemVenda records")
    @GetMapping
    public List<ItemVendaResponse> findAll() {
        return itemVendaService.findAll();
    }

    @Operation(summary = "Get a ItemVenda by id")
    @GetMapping("/{id}")
    public ItemVendaResponse findById(@PathVariable Long id) {
        return itemVendaService.findById(id);
    }

    @Operation(summary = "Create a new ItemVenda")
    @PostMapping
    public ResponseEntity<ItemVendaResponse> create(@Valid @RequestBody ItemVendaRequest request) {
        ItemVendaResponse created = itemVendaService.create(request);
        return ResponseEntity
            .created(URI.create("/api/itemVendas/" + created.id()))
            .body(created);
    }

    @Operation(summary = "Update an existing ItemVenda")
    @PutMapping("/{id}")
    public ItemVendaResponse update(@PathVariable Long id,
                                        @Valid @RequestBody ItemVendaRequest request) {
        return itemVendaService.update(id, request);
    }

    @Operation(summary = "Delete a ItemVenda by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        itemVendaService.delete(id);
    }
}
