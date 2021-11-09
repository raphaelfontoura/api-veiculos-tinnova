package com.tinnova.vehicles_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.vehicles_system.dto.VehicleDTO;
import com.tinnova.vehicles_system.dto.VehicleInputDTO;
import com.tinnova.vehicles_system.entities.Vehicle;
import com.tinnova.vehicles_system.factory.VehicleFactory;
import com.tinnova.vehicles_system.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.InvalidParameterException;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void setUp() {
        this.vehicle = VehicleFactory.createVehicle();
        this.vehicleDTO = VehicleFactory.createVehicleDTO();
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
}
