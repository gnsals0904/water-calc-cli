package com.vutz.watercalccli.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountJsonDto {

    @JsonProperty("아이디")
    private final long id;

    @JsonProperty("비밀번호")
    private final String password;

    @JsonProperty("이름")
    private final String name;

    public Account toAccount(){
        return new Account(id, password, name);
    }
}
