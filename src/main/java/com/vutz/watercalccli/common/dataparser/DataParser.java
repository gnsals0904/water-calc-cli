package com.vutz.watercalccli.common.dataparser;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.tariff.dto.Tariff;
import java.util.List;

public interface DataParser {
    List<Tariff> loadTariffs();
    List<Account> loadAccounts();
}
