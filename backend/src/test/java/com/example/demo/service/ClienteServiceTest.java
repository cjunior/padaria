package com.example.demo.service;

import com.example.demo.dto.ClienteRequest;
import com.example.demo.dto.ClienteResponse;
import com.example.demo.entity.Cliente;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock private ClienteRepository clienteRepository;
    @Mock private ClienteMapper clienteMapper;

    @InjectMocks private ClienteService clienteService;

    @Test
    void create_persistsAndReturnsResponse() {
        ClienteRequest request = new ClienteRequest("sample", "sample", "sample");
        Cliente entity = new Cliente();
        ClienteResponse response = new ClienteResponse(1L, "sample", "sample", "sample", java.time.LocalDateTime.now());

        when(clienteMapper.toEntity(request)).thenReturn(entity);
        when(clienteRepository.save(entity)).thenReturn(entity);
        when(clienteMapper.toResponse(entity)).thenReturn(response);

        ClienteResponse result = clienteService.create(request);

        assertThat(result).isEqualTo(response);
        verify(clienteRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> clienteService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(clienteRepository, never()).deleteById(any());
    }
}
