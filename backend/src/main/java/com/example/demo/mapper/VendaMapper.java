package com.example.demo.mapper;

import com.example.demo.dto.VendaRequest;
import com.example.demo.dto.VendaResponse;
import com.example.demo.entity.Venda;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class VendaMapper {

    private final ClienteRepository clienteRepository;

    public VendaMapper(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Venda toEntity(VendaRequest request) {
        Venda entity = new Venda();
        if (request.valorTotal() != null) {
            entity.setValorTotal(request.valorTotal());
        }
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
        if (request.formaPagamento() != null) {
            entity.setFormaPagamento(request.formaPagamento());
        }
        if (request.clienteId() != null) {
            entity.setCliente(clienteRepository.getReferenceById(request.clienteId()));
        }
        return entity;
    }

    public VendaResponse toResponse(Venda entity) {
        return new VendaResponse(
            entity.getId(),
            entity.getValorTotal(),
            entity.getStatus(),
            entity.getFormaPagamento(),
            entity.getCriadoEm(),
            entity.getCliente() != null ? entity.getCliente().getId() : null
        );
    }

    public void updateEntity(Venda entity, VendaRequest request) {
        if (request.valorTotal() != null) {
            entity.setValorTotal(request.valorTotal());
        }
        if (request.status() != null) {
            entity.setStatus(request.status());
        }
        if (request.formaPagamento() != null) {
            entity.setFormaPagamento(request.formaPagamento());
        }
        if (request.clienteId() != null) {
            entity.setCliente(clienteRepository.getReferenceById(request.clienteId()));
        }
    }
}
