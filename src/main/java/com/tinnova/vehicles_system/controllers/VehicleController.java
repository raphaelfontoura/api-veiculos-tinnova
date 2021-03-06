package com.tinnova.vehicles_system.controllers;

import com.tinnova.vehicles_system.dto.DecadeDTO;
import com.tinnova.vehicles_system.dto.QueryDTO;
import com.tinnova.vehicles_system.dto.VehicleDTO;
import com.tinnova.vehicles_system.dto.VehicleInputDTO;
import com.tinnova.vehicles_system.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> findAll() {
        var vehicles = service.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleInputDTO input) {
        var vehicleDto = service.create(input);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vehicleDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(vehicleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> update(@PathVariable Long id, @Valid @RequestBody VehicleDTO input) {
        VehicleDTO vehicleDTO = service.update(id, input);
        return ResponseEntity.ok(vehicleDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleDTO> patchVehicle(@PathVariable Long id, @RequestBody VehicleDTO input) {
        VehicleDTO vehicleDTO = service.patch(id, input);
        return ResponseEntity.ok(vehicleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<QueryDTO>> query(@RequestParam String q) {
        List<QueryDTO> queryDTOS = new ArrayList<>();
        if (q.equalsIgnoreCase("decada")) {
            queryDTOS = service.getDecadeRange();
        }
        if (q.equalsIgnoreCase("marca")) {
            queryDTOS = service.getCountByManufacturer();
        }
        return ResponseEntity.ok(queryDTOS);
    }

}
