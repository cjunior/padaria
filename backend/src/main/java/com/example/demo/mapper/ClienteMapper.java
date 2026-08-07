package com.example.demo.mapper;

import com.example.demo.dto.ClienteRequest;
import com.example.demo.dto.ClienteResponse;
import com.example.demo.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        Cliente entity = new Cliente();
        entity.setNome(request.nome());
        if (request.telefone() != null) {
            entity.setTelefone(request.telefone());
        }
        if (request.email() != null) {
            entity.setEmail(request.email());
        }
        return entity;
    }

    public ClienteResponse toResponse(Cliente entity) {
        return new ClienteResponse(
            entity.getId(),
            entity.getNome(),
            entity.getTelefone(),
            entity.getEmail(),
            entity.getCriadoEm()
        );
    }

    public void updateEntity(Cliente entity, ClienteRequest request) {
        entity.setNome(request.nome());
        if (request.telefone() != null) {
            entity.setTelefone(request.telefone());
        }
        if (request.email() != null) {
            entity.setEmail(request.email());
        }
    }
}
