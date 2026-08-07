package com.example.demo.controller;

import com.example.demo.dto.ItemVendaRequest;
import com.example.demo.dto.ItemVendaResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ItemVendaService;
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

@WebMvcTest(ItemVendaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemVendaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private ItemVendaService itemVendaService;

    @Test
    void create_returns201() throws Exception {
        ItemVendaRequest request = new ItemVendaRequest(1, java.math.BigDecimal.ONE, java.math.BigDecimal.ONE, 1L, 1L);
        ItemVendaResponse response = new ItemVendaResponse(1L, 1, java.math.BigDecimal.ONE, java.math.BigDecimal.ONE, 1L, 1L);

        when(itemVendaService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/itemVendas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void findById_whenMissing_returns404() throws Exception {
        when(itemVendaService.findById(eq(1L)))
            .thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/api/itemVendas/" + 1L))
            .andExpect(status().isNotFound());
    }
}
