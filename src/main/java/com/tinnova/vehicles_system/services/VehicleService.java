package com.tinnova.vehicles_system.services;

import com.tinnova.vehicles_system.dto.VehicleDTO;
import com.tinnova.vehicles_system.dto.VehicleInputDTO;
import com.tinnova.vehicles_system.entities.Vehicle;
import com.tinnova.vehicles_system.repositories.VehicleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public VehicleDTO create(VehicleInputDTO input) {
        Vehicle vehicle = Vehicle.builder()
                .veiculo(input.getVeiculo())
                .marca(input.getMarca())
                .descricao(input.getDescricao())
                .ano(input.getAno())
                .vendido(input.getVendido())
                .created(Instant.now())
                .updated(Instant.now())
                .build();
        var savedVehicle = repository.save(vehicle);
        return new VehicleDTO(savedVehicle);
    }

    public List<VehicleDTO> findAll() {
        List<Vehicle> vehicles = repository.findAll();
        return vehicles.stream().map(VehicleDTO::new).collect(Collectors.toList());
    }

    public VehicleDTO findById(Long id) {
        Vehicle vehicle = repository.findById(id).orElseThrow();
        return new VehicleDTO(vehicle);
    }

    public VehicleDTO save(VehicleInputDTO newVehicle) {
        Vehicle vehicle = newVehicle.parseDTOtoEntity();
        Vehicle saved = repository.save(vehicle);
        return new VehicleDTO(saved);
    }

    public VehicleDTO update(Long id, VehicleDTO vehicleDTO) {
        try {
            Vehicle entity = repository.getById(id);
            Vehicle update = vehicleDTO.parseDTOtoEntity();
            entity = repository.save(update);
            return new VehicleDTO(entity);
        } catch (EntityNotFoundException err) {
            throw new EntityNotFoundException("Id not found");
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new EntityNotFoundException("Id not found");
        }
    }

    public Long getSumOfNotSell() {
        return repository.countNotSell();
    }


}
