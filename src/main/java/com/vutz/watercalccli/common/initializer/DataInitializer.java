package com.vutz.watercalccli.common.initializer;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.dto.AccountJsonDto;
import com.vutz.watercalccli.account.repository.AccountRepository;
import com.vutz.watercalccli.common.dataparser.DataParser;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("====> run() 메서드 호출됨");
        List<AccountJsonDto> jsonDtos = dataParser.accounts();
        for(AccountJsonDto dto : jsonDtos){
            Account account = dto.toAccount();
            System.out.println("Account save : " + account);
            accountRepository.save(account);
        }
    }
}
