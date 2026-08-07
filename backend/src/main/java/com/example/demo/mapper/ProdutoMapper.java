package com.example.demo.mapper;

import com.example.demo.dto.ProdutoRequest;
import com.example.demo.dto.ProdutoResponse;
import com.example.demo.entity.Produto;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    private final CategoriaRepository categoriaRepository;

    public ProdutoMapper(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Produto toEntity(ProdutoRequest request) {
        Produto entity = new Produto();
        entity.setNome(request.nome());
        if (request.descricao() != null) {
            entity.setDescricao(request.descricao());
        }
        entity.setPreco(request.preco());
        if (request.quantidadeEstoque() != null) {
            entity.setQuantidadeEstoque(request.quantidadeEstoque());
        }
        if (request.ativo() != null) {
            entity.setAtivo(request.ativo());
        }
        if (request.categoriaId() != null) {
            entity.setCategoria(categoriaRepository.getReferenceById(request.categoriaId()));
        }
        return entity;
    }

    public ProdutoResponse toResponse(Produto entity) {
        return new ProdutoResponse(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getPreco(),
            entity.getQuantidadeEstoque(),
            entity.getAtivo(),
            entity.getCriadoEm(),
            entity.getCategoria() != null ? entity.getCategoria().getId() : null
        );
    }

    public void updateEntity(Produto entity, ProdutoRequest request) {
        entity.setNome(request.nome());
        if (request.descricao() != null) {
            entity.setDescricao(request.descricao());
        }
        entity.setPreco(request.preco());
        if (request.quantidadeEstoque() != null) {
            entity.setQuantidadeEstoque(request.quantidadeEstoque());
        }
        if (request.ativo() != null) {
            entity.setAtivo(request.ativo());
        }
        if (request.categoriaId() != null) {
            entity.setCategoria(categoriaRepository.getReferenceById(request.categoriaId()));
        }
    }
}
