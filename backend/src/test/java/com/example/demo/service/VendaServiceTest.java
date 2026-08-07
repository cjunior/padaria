package com.example.demo.service;

import com.example.demo.dto.VendaRequest;
import com.example.demo.dto.VendaResponse;
import com.example.demo.entity.Venda;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.VendaMapper;
import com.example.demo.repository.VendaRepository;
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
class VendaServiceTest {

    @Mock private VendaRepository vendaRepository;
    @Mock private VendaMapper vendaMapper;

    @InjectMocks private VendaService vendaService;

    @Test
    void create_persistsAndReturnsResponse() {
        VendaRequest request = new VendaRequest(java.math.BigDecimal.ONE, "sample", "sample", 1L);
        Venda entity = new Venda();
        VendaResponse response = new VendaResponse(1L, java.math.BigDecimal.ONE, "sample", "sample", java.time.LocalDateTime.now(), 1L);

        when(vendaMapper.toEntity(request)).thenReturn(entity);
        when(vendaRepository.save(entity)).thenReturn(entity);
        when(vendaMapper.toResponse(entity)).thenReturn(response);

        VendaResponse result = vendaService.create(request);

        assertThat(result).isEqualTo(response);
        verify(vendaRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vendaService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(vendaRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> vendaService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(vendaRepository, never()).deleteById(any());
    }
}
