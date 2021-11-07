package com.tinnova.vehicles_system.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant updated;

    public Vehicle(Long id, String veiculo, String marca, int ano, String descricao, boolean vendido) {
        this.id = id;
        this.veiculo = veiculo;
        this.marca = marca;
        this.ano = ano;
        this.descricao = descricao;
        this.vendido = vendido;
    }
}
