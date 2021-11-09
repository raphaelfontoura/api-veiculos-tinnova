package com.tinnova.vehicles_system.services;

import com.tinnova.vehicles_system.dto.VehicleDTO;
import com.tinnova.vehicles_system.dto.VehicleInputDTO;
import com.tinnova.vehicles_system.entities.Vehicle;
import com.tinnova.vehicles_system.factory.VehicleFactory;
import com.tinnova.vehicles_system.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    private VehicleService service;
    @Mock
    private VehicleRepository repository;

    private Vehicle vehicle;
    private VehicleDTO vehicleDTO;
    private Long validId;

    @BeforeEach
    void setUp() {
        vehicle = VehicleFactory.createVehicle();
        vehicleDTO = VehicleFactory.createVehicleDTO();
        validId = 1L;

        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(repository.getById(validId)).thenReturn(vehicle);
        doNothing().when(repository).deleteById(validId);
        when(repository.findAll()).thenReturn(List.of(vehicle));
        when(repository.countNotSell()).thenReturn(1);
    }

    @Test
    void create_shouldReturnNewVehicle_whenValidValuesInformed() {
        VehicleInputDTO input = VehicleFactory.createVehicleInput();
        Vehicle newVehicle = VehicleFactory.createVehicle();
        newVehicle.setId(null);

        var result = service.save(input);

        verify(repository, times(1)).save(newVehicle);
        assertEquals(input.getVeiculo(), result.getVeiculo());
    }

    @Test
    void update_shouldReturnVehicleUpdated_whenValidValuesInformed() {
        VehicleDTO result = service.update(validId, vehicleDTO);

        assertEquals(vehicleDTO.getVeiculo(), result.getVeiculo());
        verify(repository, times(1)).save(vehicle);
    }

    @Test
    void delete_shouldDeleteAVehicle_whenVehicleIdExist() {
        assertDoesNotThrow(() -> service.delete(validId));
        verify(repository, times(1)).deleteById(validId);
    }

    @Test
    void listAll_shouldReturnAllVehicles() {

        List<VehicleDTO> result = service.findAll();

        verify(repository, times(1)).findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getSumOfNotSell_shouldReturnSumOfVehiclesWithSellFalse() {
        Integer expected = 1;

        Integer result = service.getSumOfNotSell();

        assertEquals(expected, result);
    }

    @Test
    void getSumByDecade_shouldReturnSumOfVehiclesByDecade() {


    }

    @Test
    void getSumByManufacturer_shouldReturnSumOfVehiclesByManufacturer() {
    }

    @Test
    void getAllVehiclesLastWeek_shouldReturnAllVehiclesByCreatedAtLastWeek() {
    }

}
