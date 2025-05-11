package com.vutz.watercalccli.common.formatter;

import com.vutz.watercalccli.tariff.dto.Tariff;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("eng")
public class EnglishOutputFormatter implements OutPutFormatter {

    @Override
    public String format(Tariff tariff, int usagePrice) {
        return String.format("city: %s, sector: %s, unit price(won): %d, bill total(won): %d",
                tariff.getCity(),
                tariff.getSector(),
                tariff.getUnitPrice(),
                usagePrice);
    }

    @Override
    public String error() {
        return "Unable to calculate the bill";
    }
}
