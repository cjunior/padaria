package com.example.demo.service;

import com.example.demo.dto.ClienteRequest;
import com.example.demo.dto.ClienteResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository,
                                ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
            .map(clienteMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) {
        Cliente entity = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente not found: " + id));
        return clienteMapper.toResponse(entity);
    }

    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        Cliente entity = clienteMapper.toEntity(request);
        Cliente saved = clienteRepository.save(entity);
        return clienteMapper.toResponse(saved);
    }

    @Transactional
    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente entity = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente not found: " + id));
        clienteMapper.updateEntity(entity, request);
        return clienteMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente not found: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
