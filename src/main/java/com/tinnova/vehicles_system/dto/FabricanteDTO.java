package com.tinnova.vehicles_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class FabricanteDTO implements QueryDTO{
    @Setter
    private String nome;
    @Getter
    @Setter
    private Integer quantidade;

    @JsonProperty("marca")
    public String getNome() {
        return nome;
    }

    public FabricanteDTO(String line) {
        QueryDTOConvert(line);
    }
    @Override
    public QueryDTO QueryDTOConvert(String convert) {
        String[] split = convert.split(",");
        this.nome = split[0];
        this.quantidade = Integer.parseInt(split[1]);
        return this;
    }
}
