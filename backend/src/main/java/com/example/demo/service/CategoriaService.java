package com.example.demo.service;

import com.example.demo.dto.CategoriaRequest;
import com.example.demo.dto.CategoriaResponse;
import com.example.demo.entity.Categoria;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CategoriaMapper;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository,
                                CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll().stream()
            .map(categoriaMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse findById(Long id) {
        Categoria entity = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria not found: " + id));
        return categoriaMapper.toResponse(entity);
    }

    @Transactional
    public CategoriaResponse create(CategoriaRequest request) {
        Categoria entity = categoriaMapper.toEntity(request);
        Categoria saved = categoriaRepository.save(entity);
        return categoriaMapper.toResponse(saved);
    }

    @Transactional
    public CategoriaResponse update(Long id, CategoriaRequest request) {
        Categoria entity = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria not found: " + id));
        categoriaMapper.updateEntity(entity, request);
        return categoriaMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria not found: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
