package com.example.demo.service;

import com.example.demo.dto.ItemVendaRequest;
import com.example.demo.dto.ItemVendaResponse;
import com.example.demo.entity.ItemVenda;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ItemVendaMapper;
import com.example.demo.repository.ItemVendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaService(ItemVendaRepository itemVendaRepository,
                                ItemVendaMapper itemVendaMapper) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    @Transactional(readOnly = true)
    public List<ItemVendaResponse> findAll() {
        return itemVendaRepository.findAll().stream()
            .map(itemVendaMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public ItemVendaResponse findById(Long id) {
        ItemVenda entity = itemVendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ItemVenda not found: " + id));
        return itemVendaMapper.toResponse(entity);
    }

    @Transactional
    public ItemVendaResponse create(ItemVendaRequest request) {
        ItemVenda entity = itemVendaMapper.toEntity(request);
        ItemVenda saved = itemVendaRepository.save(entity);
        return itemVendaMapper.toResponse(saved);
    }

    @Transactional
    public ItemVendaResponse update(Long id, ItemVendaRequest request) {
        ItemVenda entity = itemVendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ItemVenda not found: " + id));
        itemVendaMapper.updateEntity(entity, request);
        return itemVendaMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!itemVendaRepository.existsById(id)) {
            throw new ResourceNotFoundException("ItemVenda not found: " + id);
        }
        itemVendaRepository.deleteById(id);
    }
}
