package com.vutz.watercalccli.tariff.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
@RequiredArgsConstructor
public class PriceResponse {
    private final Tariff tariff;
    private final int usagePrice;
}
