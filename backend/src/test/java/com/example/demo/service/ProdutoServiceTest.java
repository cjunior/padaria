package com.example.demo.service;

import com.example.demo.dto.ProdutoRequest;
import com.example.demo.dto.ProdutoResponse;
import com.example.demo.entity.Produto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProdutoMapper;
import com.example.demo.repository.ProdutoRepository;
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
class ProdutoServiceTest {

    @Mock private ProdutoRepository produtoRepository;
    @Mock private ProdutoMapper produtoMapper;

    @InjectMocks private ProdutoService produtoService;

    @Test
    void create_persistsAndReturnsResponse() {
        ProdutoRequest request = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, 1, true, 1L);
        Produto entity = new Produto();
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, 1, true, java.time.LocalDateTime.now(), 1L);

        when(produtoMapper.toEntity(request)).thenReturn(entity);
        when(produtoRepository.save(entity)).thenReturn(entity);
        when(produtoMapper.toResponse(entity)).thenReturn(response);

        ProdutoResponse result = produtoService.create(request);

        assertThat(result).isEqualTo(response);
        verify(produtoRepository).save(entity);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> produtoService.findById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_whenMissing_throwsNotFound() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> produtoService.delete(1L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(produtoRepository, never()).deleteById(any());
    }
}
