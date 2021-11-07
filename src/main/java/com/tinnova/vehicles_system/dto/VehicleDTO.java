package com.tinnova.vehicles_system.dto;

import com.tinnova.vehicles_system.entities.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String veiculo;
    private String marca;
    private Integer ano;
    private String descricao;
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
        Vehicle vehicle = Vehicle.builder()
                .id(id)
                .veiculo(veiculo)
                .marca(marca)
                .ano(ano)
                .descricao(descricao)
                .vendido(vendido)
                .created(created)
                .updated(updated)
                .build();
        return vehicle;
    }
}
