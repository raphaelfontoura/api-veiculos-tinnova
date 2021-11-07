package com.tinnova.vehicles_system.factory;

import com.tinnova.vehicles_system.dto.VehicleDTO;
import com.tinnova.vehicles_system.dto.VehicleInputDTO;
import com.tinnova.vehicles_system.entities.Vehicle;

public class VehicleFactory {

    private static Long id = 1L;
    private static String veiculo = "Versa", marca = "Nissan";
    private static String descricao = "Nissan Versa completo";
    private static boolean vendido = false;
    private static int ano = 2019;

    public static Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle(id,
                veiculo, marca, ano,
                descricao, vendido);
        return vehicle;
    }

    public static VehicleDTO createVehicleDTO() {
        return new VehicleDTO(createVehicle());
    }

    public static VehicleInputDTO createVehicleInput() {
        VehicleInputDTO newVehicleInput = new VehicleInputDTO(veiculo,
                marca,
                ano,
                descricao,
                vendido);
        return newVehicleInput;
    }

}
