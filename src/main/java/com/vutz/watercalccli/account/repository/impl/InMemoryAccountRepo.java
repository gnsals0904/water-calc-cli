package com.vutz.watercalccli.account.repository.impl;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.exception.DuplicatedAccountIdException;
import com.vutz.watercalccli.account.repository.AccountRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAccountRepo implements AccountRepository {

    private final Map<Long, Account> accountMap = new HashMap<>();

    @Override
    public void save(Account account) {
        if(accountMap.containsKey(account.getId()))
            throw new DuplicatedAccountIdException(account.getId());
        accountMap.put(account.getId(), account);
    }

    @Override
    public Optional<Account> findByIdAndPw(long id, String password) {
        Account account = accountMap.get(id);
        if(Objects.nonNull(account) && account.getPassword().equals(password))
            return Optional.of(account);
        return Optional.empty();
    }

    @Override
    public void removeAll() {
        accountMap.clear();
    }
}
