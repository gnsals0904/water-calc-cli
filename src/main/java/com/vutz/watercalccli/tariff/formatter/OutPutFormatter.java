package com.vutz.watercalccli.tariff.formatter;

import com.vutz.watercalccli.tariff.dto.Tariff;

public interface OutPutFormatter {
    String format(Tariff tariff, int usagePrice);
    String error();
}
