package com.vutz.watercalccli.common.dataparser;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.price.dto.Price;
import java.util.List;

public class JsonDataParser implements DataParser {

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
    public List<Account> accounts() {
        return List.of();
    }
}
