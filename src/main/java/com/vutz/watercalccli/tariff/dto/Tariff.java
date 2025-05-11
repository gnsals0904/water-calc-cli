package com.vutz.watercalccli.tariff.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Tariff {
    private final long id;
    private final String city;
    private final String sector;
    private final int sectionStart;
    private final int sectionEnd;
    private final int unitPrice;

    public boolean contains(int value) {
        return sectionStart <= value && value <= sectionEnd;
    }

    public int calcUsagePrice(int usage){
        return unitPrice * usage;
    }
}
