package com.example.demo.mapper;

import com.example.demo.dto.CategoriaRequest;
import com.example.demo.dto.CategoriaResponse;
import com.example.demo.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequest request) {
        Categoria entity = new Categoria();
        entity.setNome(request.nome());
        if (request.descricao() != null) {
            entity.setDescricao(request.descricao());
        }
        if (request.ativo() != null) {
            entity.setAtivo(request.ativo());
        }
        return entity;
    }

    public CategoriaResponse toResponse(Categoria entity) {
        return new CategoriaResponse(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getAtivo(),
            entity.getCriadoEm()
        );
    }

    public void updateEntity(Categoria entity, CategoriaRequest request) {
        entity.setNome(request.nome());
        if (request.descricao() != null) {
            entity.setDescricao(request.descricao());
        }
        if (request.ativo() != null) {
            entity.setAtivo(request.ativo());
        }
    }
}
