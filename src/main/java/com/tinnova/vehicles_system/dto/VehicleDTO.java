package com.tinnova.vehicles_system.dto;

import com.tinnova.vehicles_system.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    @NotBlank(message = "Informe o nome do veículo")
    private String veiculo;
    @NotBlank(message = "Informe a marca do veículo")
    private String marca;
    @NotNull
    @Digits(integer = 4, message = "Digite o ano no formato yyyy", fraction = 0)
    @Min(value = 1900, message = "Digite o ano no formato yyyy")
    private Integer ano;
    @NotNull(message = "Necessário informar o campo descrição")
    private String descricao;
    @NotNull(message = "Necessário informar o campo vendido")
    private Boolean vendido;
    private Instant created;
    private Instant updated;

    public VehicleDTO(Vehicle entity) {
        this.id = entity.getId();
        this.veiculo = entity.getVeiculo();
        this.marca = entity.getMarca();
        this.ano = entity.getAno();
        this.descricao = entity.getDescricao();
        this.vendido = entity.getVendido();
        this.created = entity.getCreated();
        this.updated = entity.getUpdated();
    }

    public Vehicle parseDTOtoEntity() {
        return Vehicle.builder()
                .id(id)
                .veiculo(veiculo)
                .marca(marca)
                .ano(ano)
                .descricao(descricao)
                .vendido(vendido)
                .created(created)
                .updated(updated)
                .build();
    }
}
