package com.vutz.watercalccli.common.formatter;

import com.vutz.watercalccli.tariff.dto.Tariff;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class KoreanOutputFormatter implements OutPutFormatter {

    @Override
    public String format(Tariff tariff, int usagePrice) {
        return String.format("지자체명: %s, 업종: %s, 구간금액(원): %d, 총금액(원): %d",
                tariff.getCity(),
                tariff.getSector(),
                tariff.getUnitPrice(),
                usagePrice);
    }

    @Override
    public String error() {
        return "해당 요금을 계산할 수 없습니다";
    }
}
