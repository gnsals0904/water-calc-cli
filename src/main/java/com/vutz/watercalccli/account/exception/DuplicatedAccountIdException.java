package com.vutz.watercalccli.account.exception;

public class DuplicatedAccountIdException extends RuntimeException {
    public DuplicatedAccountIdException(long id) {
        super(String.format("계정 ID %d 는 이미 저장된 계정입니다", id));
    }
}
