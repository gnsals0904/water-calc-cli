package com.vutz.watercalccli.common.dataparser;

import com.vutz.watercalccli.account.dto.AccountJsonDto;
import com.vutz.watercalccli.price.dto.Price;
import java.util.List;

public interface DataParser {

    List<String> cities();

    List<String> sectors(String city);

    Price price(String city, String sector);

    List<AccountJsonDto> accounts();
}
