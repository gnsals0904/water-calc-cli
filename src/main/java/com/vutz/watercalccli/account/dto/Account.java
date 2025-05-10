package com.vutz.watercalccli.account.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Account {
    private final long id;
    private final String password;
    private final String name;
}
