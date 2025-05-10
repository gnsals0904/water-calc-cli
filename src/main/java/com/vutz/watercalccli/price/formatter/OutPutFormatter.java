package com.vutz.watercalccli.price.formatter;

import com.vutz.watercalccli.price.dto.Price;

public interface OutPutFormatter {
    String format(Price price, int usage);
}
