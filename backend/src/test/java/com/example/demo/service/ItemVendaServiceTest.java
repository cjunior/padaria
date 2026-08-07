package com.example.demo.service;

import com.example.demo.dto.ItemVendaRequest;
import com.example.demo.dto.ItemVendaResponse;
import com.example.demo.entity.ItemVenda;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ItemVendaMapper;
import com.example.demo.repository.ItemVendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemVendaServiceTest {

    @Mock private ItemVendaRepository itemVendaRepository;
    @Mock private ItemVendaMapper itemVendaMapper;

    @InjectMocks private ItemVendaService itemVendaService;

    @Test
    void create_persistsAndReturnsResponse() {
        ItemVendaRequest request = new ItemVendaRequest(1, java.math.BigDecimal.ONE, java.math.BigDecimal.ONE, 1L, 1L);
        ItemVenda entity = new ItemVenda();
        ItemVendaResponse response = new ItemVendaResponse(1L, 1, java.math.BigDecimal.ONE, java.math.BigDecimal.ONE, 1L, 1L);

        when(itemVendaMapper.toEntity(request)).thenReturn(entity);
        when(itemVendaRepository.save(entity)).thenReturn(entity);
        when(itemVendaMapper.toResponse(entity)).thenReturn(response);

        ItemVendaResponse result = itemVendaService.create(request);

        assertThat(result).isEqualTo(response);
        verify(itemVendaRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(itemVendaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> itemVendaService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(itemVendaRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> itemVendaService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(itemVendaRepository, never()).deleteById(any());
    }
}
