package com.tinnova.vehicles_system.repositories;

import com.tinnova.vehicles_system.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.vendido=false")
    long countNotSell();
}
