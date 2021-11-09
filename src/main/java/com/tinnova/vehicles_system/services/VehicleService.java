package com.tinnova.vehicles_system.services;

import com.tinnova.vehicles_system.dto.*;
import com.tinnova.vehicles_system.entities.Vehicle;
import com.tinnova.vehicles_system.repositories.VehicleRepository;
import com.tinnova.vehicles_system.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public VehicleDTO create(VehicleInputDTO input) {
        Vehicle vehicle = Vehicle.builder()
                .veiculo(input.getVeiculo())
                .marca(input.getMarca())
                .descricao(input.getDescricao())
                .ano(input.getAno())
                .vendido(input.getVendido())
//                .created(Instant.now())
//                .updated(Instant.now())
                .build();
        var savedVehicle = repository.save(vehicle);
        return new VehicleDTO(savedVehicle);
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> findAll() {
        List<Vehicle> vehicles = repository.findAll();
        return vehicles.stream().map(VehicleDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found. Id:"+id));
        return new VehicleDTO(vehicle);
    }

    @Transactional
    public VehicleDTO save(VehicleInputDTO newVehicle) {
        Vehicle vehicle = newVehicle.parseDTOtoEntity();
        Vehicle saved = repository.save(vehicle);
        return new VehicleDTO(saved);
    }

    @Transactional
    public VehicleDTO update(Long id, VehicleDTO vehicleDTO) {
        try {
            Vehicle entity = repository.getById(id);
            Vehicle update = vehicleDTO.parseDTOtoEntity();
            entity = repository.save(update);
            return new VehicleDTO(entity); // TO FIX: atributo updated não atualiza no DTO.
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public VehicleDTO patch(Long id, VehicleDTO input) {
        try {
            Vehicle entity = repository.getById(id);
            setValues(entity, input);

            Vehicle save = repository.save(entity);
            return new VehicleDTO(save); // TO FIX: atributo updated não atualiza no DTO.
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional(readOnly = true)
    public Integer getSumOfNotSell() {
        return repository.countNotSell();
    }

    public List<QueryDTO> getDecadeRange() {
        List<String> resultQuery = repository.countDecada();
        return resultQuery.stream().map(line -> {
            QueryDTO dto = new DecadeDTO(line);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<QueryDTO> getCountByManufacturer() {
        List<String> resultQuery = repository.countFabricante();
        return resultQuery.stream().map(line -> {
            QueryDTO dto = new FabricanteDTO(line);
            return dto;
        }).collect(Collectors.toList());
    }

    private void setValues(Vehicle entity, VehicleDTO input) {
        if (input == null) return;
        if (input.getVeiculo() != null) entity.setVeiculo(input.getVeiculo());
        if (input.getMarca() != null) entity.setMarca(input.getMarca());
        if (input.getDescricao() != null) entity.setDescricao(input.getDescricao());
        if (input.getVendido() != null) entity.setVendido(input.getVendido());
        if (input.getAno() != null) entity.setAno(input.getAno());
    }

}
