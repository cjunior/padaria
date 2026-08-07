package com.example.demo.mapper;

import com.example.demo.dto.ItemVendaRequest;
import com.example.demo.dto.ItemVendaResponse;
import com.example.demo.entity.ItemVenda;
import com.example.demo.repository.VendaRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemVendaMapper {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public ItemVendaMapper(VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    public ItemVenda toEntity(ItemVendaRequest request) {
        ItemVenda entity = new ItemVenda();
        entity.setQuantidade(request.quantidade());
        entity.setPrecoUnitario(request.precoUnitario());
        entity.setSubtotal(request.subtotal());
        if (request.vendaId() != null) {
            entity.setVenda(vendaRepository.getReferenceById(request.vendaId()));
        }
        if (request.produtoId() != null) {
            entity.setProduto(produtoRepository.getReferenceById(request.produtoId()));
        }
        return entity;
    }

    public ItemVendaResponse toResponse(ItemVenda entity) {
        return new ItemVendaResponse(
            entity.getId(),
            entity.getQuantidade(),
            entity.getPrecoUnitario(),
            entity.getSubtotal(),
            entity.getVenda() != null ? entity.getVenda().getId() : null,
            entity.getProduto() != null ? entity.getProduto().getId() : null
        );
    }

    public void updateEntity(ItemVenda entity, ItemVendaRequest request) {
        entity.setQuantidade(request.quantidade());
        entity.setPrecoUnitario(request.precoUnitario());
        entity.setSubtotal(request.subtotal());
        if (request.vendaId() != null) {
            entity.setVenda(vendaRepository.getReferenceById(request.vendaId()));
        }
        if (request.produtoId() != null) {
            entity.setProduto(produtoRepository.getReferenceById(request.produtoId()));
        }
    }
}
