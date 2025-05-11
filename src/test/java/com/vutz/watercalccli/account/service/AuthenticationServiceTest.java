package com.vutz.watercalccli.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.exception.NotFoundAccountException;
import com.vutz.watercalccli.account.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticationService 단위 테스트")
class AuthenticationServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private final Account testAccount = new Account(1, "password", "testUser");


    @Test
    @DisplayName("id와 pw가 일치하면 로그인이 성공하고 같은 Account 객체를 리턴받는다")
    void login() {
        // given
        when(accountRepository.findByIdAndPw(1L, "password"))
                .thenReturn(Optional.of(testAccount));

        // when
        Account account = authenticationService.login(1L, "password");

        //then
        assertEquals(testAccount, account);
    }

    @Test
    @DisplayName("id와 pw가 일치하면 로그인 성공 후, currentAccount에 유저 정보가 저장된다")
    void checkCurrentAccount() {
        // given
        when(accountRepository.findByIdAndPw(1L, "password"))
                .thenReturn(Optional.of(testAccount));

        // when
        authenticationService.login(1L, "password");

        //then
        assertTrue(authenticationService.getCurrentAccount().isPresent());
        assertEquals(testAccount, authenticationService.getCurrentAccount().get());
    }


    @Test
    @DisplayName("id, pw가 일치하지 않으면 NotFoundAccountException 예외가 발생하고, CurrnetAccount에는 아무것도 저장되지 않는다")
    void loginFailure() {
        // given
        when(accountRepository.findByIdAndPw(anyLong(), anyString()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundAccountException.class, () -> {
            authenticationService.login(99L, "wrong");
        });
        assertTrue(authenticationService.getCurrentAccount().isEmpty());
    }

    @Test
    @DisplayName("logout() 호출하면 currentAccount 정보는 삭제된다")
    void logout() {
        // given
        when(accountRepository.findByIdAndPw(1L, "pass"))
                .thenReturn(Optional.of(testAccount));
        authenticationService.login(1L, "pass");
        assertTrue(authenticationService.getCurrentAccount().isPresent());

        // when
        authenticationService.logout();

        // then
        assertTrue(authenticationService.getCurrentAccount().isEmpty());
    }

    @Test
    @DisplayName("getCurrentAccount 메서드는 현재 로그인된 사용자를 Optional로 반환한다")
    void getCurrentAccount() {
        // given
        assertTrue(authenticationService.getCurrentAccount().isEmpty());
        when(accountRepository.findByIdAndPw(1L, "pass"))
                .thenReturn(Optional.of(testAccount));
        authenticationService.login(1L, "pass");

        // then
        assertTrue(authenticationService.getCurrentAccount().isPresent());
        assertEquals(testAccount, authenticationService.getCurrentAccount().get());
    }
}