package com.vutz.watercalccli.account.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AccountJsonDto 기능 테스트")
class AccountJsonDtoTest {

    @Test
    @DisplayName("toAccount 메서드 호출 시 Account 객체로 변환된다")
    void toAccount() {
        // given
        long id = 1L;
        String password = "pw1234";
        String name = "hunmin";
        AccountJsonDto dto = new AccountJsonDto(id, password, name);

        // when
        Account account = dto.toAccount();

        // then
        assertEquals(id, account.getId());
        assertEquals(password, account.getPassword());
        assertEquals(name, account.getName());
    }
}