package com.example.demo.service;

import com.example.demo.dto.CategoriaRequest;
import com.example.demo.dto.CategoriaResponse;
import com.example.demo.entity.Categoria;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CategoriaMapper;
import com.example.demo.repository.CategoriaRepository;
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
class CategoriaServiceTest {

    @Mock private CategoriaRepository categoriaRepository;
    @Mock private CategoriaMapper categoriaMapper;

    @InjectMocks private CategoriaService categoriaService;

    @Test
    void create_persistsAndReturnsResponse() {
        CategoriaRequest request = new CategoriaRequest("sample", "sample", true);
        Categoria entity = new Categoria();
        CategoriaResponse response = new CategoriaResponse(1L, "sample", "sample", true, java.time.LocalDateTime.now());

        when(categoriaMapper.toEntity(request)).thenReturn(entity);
        when(categoriaRepository.save(entity)).thenReturn(entity);
        when(categoriaMapper.toResponse(entity)).thenReturn(response);

        CategoriaResponse result = categoriaService.create(request);

        assertThat(result).isEqualTo(response);
        verify(categoriaRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(categoriaRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> categoriaService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(categoriaRepository, never()).deleteById(any());
    }
}
