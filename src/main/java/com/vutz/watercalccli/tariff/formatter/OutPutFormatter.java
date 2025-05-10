package com.vutz.watercalccli.tariff.formatter;

import com.vutz.watercalccli.tariff.dto.Price;

public interface OutPutFormatter {
    String format(Price price, int usage);
}
