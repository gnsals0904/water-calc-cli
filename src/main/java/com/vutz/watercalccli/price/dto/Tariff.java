package com.vutz.watercalccli.price.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Tariff {
    private final long id;
    private final String city;
    private final String sector;
    private final int sectionStart;
    private final int sectionEnd;
    private final int unitPrice;
}
