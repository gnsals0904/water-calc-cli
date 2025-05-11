package com.vutz.watercalccli.account.exception;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {
        super("로그인이 필요합니다");
    }
}
