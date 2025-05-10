package com.vutz.watercalccli.common.initializer;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.repository.AccountRepository;
import com.vutz.watercalccli.common.dataparser.DataParser;
import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.repository.TariffRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(InteractiveShellRunner.PRECEDENCE - 1)
public class DataInitializer implements ApplicationRunner {

    private final DataParser dataParser;
    private final AccountRepository accountRepository;
    private final TariffRepository tariffRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Account> accounts = dataParser.loadAccounts();
        System.out.println(accounts);
        accounts.forEach(accountRepository::save);

        List<Tariff> tariffs = dataParser.loadTariffs();
        System.out.println(tariffs);
        tariffs.forEach(tariffRepository::save);
    }
}
