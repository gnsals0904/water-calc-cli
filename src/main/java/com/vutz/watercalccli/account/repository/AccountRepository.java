package com.vutz.watercalccli.account.repository;

import com.vutz.watercalccli.account.dto.Account;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findByIdAndPw(long id, String password);
    void removeAll();
}
