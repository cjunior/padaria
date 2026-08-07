package com.example.demo.controller;

import com.example.demo.dto.ProdutoRequest;
import com.example.demo.dto.ProdutoResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ProdutoService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProdutoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private ProdutoService produtoService;

    @Test
    void create_returns201() throws Exception {
        ProdutoRequest request = new ProdutoRequest("sample", "sample", java.math.BigDecimal.ONE, 1, true, 1L);
        ProdutoResponse response = new ProdutoResponse(1L, "sample", "sample", java.math.BigDecimal.ONE, 1, true, java.time.LocalDateTime.now(), 1L);

        when(produtoService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(produtoService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/produtos/" + 1L))
            .andExpect(status().isNotFound());
    }
}
