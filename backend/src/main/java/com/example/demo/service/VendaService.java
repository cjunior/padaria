package com.example.demo.service;

import com.example.demo.dto.VendaRequest;
import com.example.demo.dto.VendaResponse;
import com.example.demo.entity.Venda;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.VendaMapper;
import com.example.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;

    public VendaService(VendaRepository vendaRepository,
                                VendaMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> findAll() {
        return vendaRepository.findAll().stream()
            .map(vendaMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public VendaResponse findById(Long id) {
        Venda entity = vendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venda not found: " + id));
        return vendaMapper.toResponse(entity);
    }

    @Transactional
    public VendaResponse create(VendaRequest request) {
        Venda entity = vendaMapper.toEntity(request);
        Venda saved = vendaRepository.save(entity);
        return vendaMapper.toResponse(saved);
    }

    @Transactional
    public VendaResponse update(Long id, VendaRequest request) {
        Venda entity = vendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venda not found: " + id));
        vendaMapper.updateEntity(entity, request);
        return vendaMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!vendaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venda not found: " + id);
        }
        vendaRepository.deleteById(id);
    }
}
