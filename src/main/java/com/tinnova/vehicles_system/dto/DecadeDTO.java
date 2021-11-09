package com.tinnova.vehicles_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DecadeDTO implements QueryDTO {

    @Setter
    private String nome;
    @Getter @Setter
    private Integer quantidade;

    @JsonProperty("decada")
    public String getNome() {
        return nome;
    }

    public DecadeDTO(String line) {
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
