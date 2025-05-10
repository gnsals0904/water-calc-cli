package com.vutz.watercalccli.common.dataparser;

import com.vutz.watercalccli.account.dto.AccountJsonDto;
import com.vutz.watercalccli.price.dto.Price;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "file.type", havingValue = "csv")
public class CsvDataParser implements DataParser {

    @Override
    public List<String> cities() {
        return List.of();
    }

    @Override
    public List<String> sectors(String city) {
        return List.of();
    }

    @Override
    public Price price(String city, String sector) {
        return null;
    }

    @Override
    public List<AccountJsonDto> accounts() {
        return List.of();
    }
}
