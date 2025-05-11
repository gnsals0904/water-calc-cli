package com.vutz.watercalccli.tariff.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PriceResponse {
    private final Tariff tariff;
    private final int usagePrice;
}
