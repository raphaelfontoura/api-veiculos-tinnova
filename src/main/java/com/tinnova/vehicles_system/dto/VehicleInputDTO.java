package com.tinnova.vehicles_system.dto;

import com.tinnova.vehicles_system.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor
public class VehicleInputDTO {

    @NotBlank(message = "Campo requerido")
    private String veiculo;
    @NotBlank(message = "Campo requerido")
    private String marca;
    @NotNull
    @Digits(integer = 4, message = "Digite o ano no formato yyyy", fraction = 0)
    @Min(value = 1900, message = "Digite o ano no formato yyyy")
    private Integer ano;
    private String descricao;
    private Boolean vendido = false;

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
