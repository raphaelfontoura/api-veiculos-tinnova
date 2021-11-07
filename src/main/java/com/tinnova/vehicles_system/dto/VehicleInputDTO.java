package com.tinnova.vehicles_system.dto;

import com.tinnova.vehicles_system.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class VehicleInputDTO {
    private String veiculo;
    private String marca;
    private Integer ano;
    private String descricao;
    private Boolean vendido;

    public Vehicle parseDTOtoEntity() {
        Vehicle vehicle = new Vehicle();
        vehicle.setAno(this.ano);
        vehicle.setVeiculo(this.veiculo);
        vehicle.setMarca(this.marca);
        vehicle.setDescricao(this.descricao);
        vehicle.setVendido(this.vendido);

        return vehicle;
    }
}
