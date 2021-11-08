package com.tinnova.vehicles_system.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "tb_veiculos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String veiculo;
    private String marca;
    private Integer ano;
    @Column(columnDefinition = "text")
    private String descricao;
    private Boolean vendido;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updated;

    public Vehicle(Long id, String veiculo, String marca, int ano, String descricao, boolean vendido) {
        this.id = id;
        this.veiculo = veiculo;
        this.marca = marca;
        this.ano = ano;
        this.descricao = descricao;
        this.vendido = vendido;
    }

    @PrePersist
    protected void prePersist() {
        if (this.vendido == null) {
            this.vendido = false;
        }
        if (this.created == null) {
            this.created = Instant.now();
        }
        if (this.updated == null) {
            this.updated = Instant.now();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        this.updated = Instant.now();
    }
}
