package com.vutz.watercalccli.account.exception;

public class NotFoundAccountException extends RuntimeException {
    public NotFoundAccountException(long id) {
        super(String.format("계정 ID %d를 찾을 수 없습니다.", id));
    }
}
