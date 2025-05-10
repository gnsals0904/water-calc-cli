package com.vutz.watercalccli.account.service;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.exception.NotFoundAccountException;
import com.vutz.watercalccli.account.repository.AccountRepository;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;

    @Getter
    private Optional<Account> currentAccount = Optional.empty();

    public Account login(Long id, String password) {
        Account account = accountRepository.findByIdAndPw(id, password)
                .orElseThrow(() -> new NotFoundAccountException(id));
        currentAccount = Optional.of(account);
        return account;
    }

    public void logout() {
        currentAccount = Optional.empty();
    }
}
