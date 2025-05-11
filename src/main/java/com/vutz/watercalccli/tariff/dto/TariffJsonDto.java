package com.vutz.watercalccli.tariff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TariffJsonDto {

    @JsonProperty("순번")
    private final long id;

    @JsonProperty("지자체명")
    private final String city;

    @JsonProperty("업종")
    private final String sector;

    @JsonProperty("구간시작(세제곱미터)")
    private final int sectionStart;

    @JsonProperty("구간끝(세제곱미터)")
    private final int sectionEnd;

    @JsonProperty("구간금액(원)")
    private final int unitPrice;

    public Tariff toTariff(){
        return new Tariff(id, city, sector, sectionStart, sectionEnd, unitPrice);
    }
}
