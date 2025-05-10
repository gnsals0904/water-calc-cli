package com.vutz.watercalccli.account.repository.impl;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.exception.DuplicatedAccountIdException;
import com.vutz.watercalccli.account.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AccountRepository 단위 테스트")
class InMemoryAccountRepoTest {

    AccountRepository accountRepository = new InMemoryAccountRepo();

    @BeforeEach
    void before(){
        accountRepository.removeAll();
        accountRepository.save(new Account(1, "1", "hunmin"));
        accountRepository.save(new Account(2, "2", "test"));
    }

    @Test
    @DisplayName("같은 id를 가진 Account 객체를 save 메서드를 통해 저장하면 DuplicatedAccountIdException이 발생한다")
    void cantSaveSameId() {
        Assertions.assertThrows(DuplicatedAccountIdException.class, () ->
            accountRepository.save(new Account(1, "1", "test")));
    }

    @Test
    @DisplayName("id와 Pw가 일치하는 Account 객체가 저장되어있다면 찾을 수 있다")
    void findByIdAndPw() {
        // given
        Account testAccount = new Account(1, "1", "hunmin");

        // when
        Optional<Account> account = accountRepository.findByIdAndPw(1, "1");

        // then
        Assertions.assertTrue(account.isPresent());
        Assertions.assertEquals(testAccount, account.get());
    }

    @Test
    @DisplayName("id가 일치하는 Account 객체가 없다면 비어있는 Optional 이 리턴된다")
    void cantFindById(){
        // when
        Optional<Account> byIdAndPw = accountRepository.findByIdAndPw(999, "1");

        // then
        Assertions.assertTrue(byIdAndPw.isEmpty());
    }

    @Test
    @DisplayName("id가 일치해도 Pw가 일치하지 않다면 비어있는 Optional 이 리턴된다")
    void cantFindByIdAndPw(){
        // when
        Optional<Account> byIdAndPw = accountRepository.findByIdAndPw(1, "2");

        // then
        Assertions.assertTrue(byIdAndPw.isEmpty());
    }

}