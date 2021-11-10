package com.tinnova.vehicles_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.vehicles_system.dto.VehicleDTO;
import com.tinnova.vehicles_system.dto.VehicleInputDTO;
import com.tinnova.vehicles_system.entities.Vehicle;
import com.tinnova.vehicles_system.factory.VehicleFactory;
import com.tinnova.vehicles_system.services.VehicleService;
import com.tinnova.vehicles_system.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.InvalidParameterException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehycleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehicleService service;
    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle vehicle;
    private VehicleDTO vehicleDTO;
    private Long existId;
    private Long noExistId;

    @BeforeEach
    void setUp() {
        this.vehicle = VehicleFactory.createVehicle();
        this.vehicleDTO = VehicleFactory.createVehicleDTO();
        this.existId = 1L;
        this.noExistId = 99L;
    }

    @Test
    public void create_shouldReturnVehicleDTO_whenValidVehicle() throws Exception {
        String jsonVehicle = objectMapper.writeValueAsString(VehicleFactory.createVehicleInput());
        when(service.create(any(VehicleInputDTO.class))).thenReturn(vehicleDTO);

        mockMvc.perform(post("/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonVehicle))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.veiculo").value(vehicleDTO.getVeiculo()));
    }

    @Test
    public void create_shouldReturnException_whenInvalidVehicle() throws Exception {
        VehicleInputDTO vehicleInput = VehicleFactory.createVehicleInput();
        vehicleInput.setAno(2);
        vehicleInput.setVeiculo(null);
        String jsonVehicle = objectMapper.writeValueAsString(vehicleInput);
        when(service.create(vehicleInput)).thenThrow(InvalidParameterException.class);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonVehicle))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void findById_shouldReturnVehicleDTO_whenIdExist() throws Exception {
        when(service.findById(existId)).thenReturn(vehicleDTO);

        mockMvc.perform(get("/veiculos/{id}",existId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.veiculo")).value(vehicleDTO.getVeiculo()));
    }

    @Test
    void findById_shouldReturnResourceNotFound_whenIdNotExist() throws Exception {
        when(service.findById(noExistId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/veiculos/{id}",noExistId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturnNoContent_whenIdExist() throws Exception {
        doNothing().when(service).delete(existId);

        mockMvc.perform(delete("/veiculos/{id}",existId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturnResourceNotFoundException_whenIdNoExist() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).delete(noExistId);

        mockMvc.perform(delete("/veiculos/{id}",noExistId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
