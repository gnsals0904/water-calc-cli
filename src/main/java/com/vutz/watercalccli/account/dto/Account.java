package com.vutz.watercalccli.account.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Account {
    private final long id;
    private final String password;
    private final String name;
}
