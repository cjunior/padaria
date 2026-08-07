package com.example.demo.controller;

import com.example.demo.dto.ClienteRequest;
import com.example.demo.dto.ClienteResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ClienteService;
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

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private ClienteService clienteService;

    @Test
    void create_returns201() throws Exception {
        ClienteRequest request = new ClienteRequest("sample", "sample", "sample");
        ClienteResponse response = new ClienteResponse(1L, "sample", "sample", "sample", java.time.LocalDateTime.now());

        when(clienteService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(clienteService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/clientes/" + 1L))
            .andExpect(status().isNotFound());
    }
}
