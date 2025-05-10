package com.vutz.watercalccli.tariff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TariffJsonDto {

    @JsonProperty("순번")
    long id;

    @JsonProperty("지자체명")
    String city;

    @JsonProperty("업종")
    String sector;

    @JsonProperty("구간시작(세제곱미터)")
    int sectionStart;

    @JsonProperty("구간끝(세제곱미터)")
    int sectionEnd;

    @JsonProperty("구간금액(원)")
    int unitPrice;

    public Tariff toTariff(){
        return new Tariff(id, city, sector, sectionStart, sectionEnd, unitPrice);
    }
}
