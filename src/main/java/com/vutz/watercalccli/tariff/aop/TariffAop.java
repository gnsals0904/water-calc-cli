package com.vutz.watercalccli.tariff.aop;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.exception.NotLoggedInException;
import com.vutz.watercalccli.account.service.AuthenticationService;
import com.vutz.watercalccli.tariff.annotation.LogTariffAction;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TariffAop {

    private final AuthenticationService authenticationService;

    @Around("@annotation(logAnno)")
    public Object logTariffAction(ProceedingJoinPoint joinPoint, LogTariffAction logAnno) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String action = logAnno.value();
        Object result = joinPoint.proceed();
        if(logAnno.secure()){
            Optional<Account> currentAccount = authenticationService.getCurrentAccount();
            if (currentAccount.isEmpty()) {
                throw new NotLoggedInException();
            }
            String userName = currentAccount.get().getName();
            String arguments = Arrays.stream(args)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            log.info("<--- User name : {} called action : {} with args : {} ---", userName, action, arguments);
            log.info("--- User name : {} action : {} result : {} --->", userName, action, result);
        }
        return result;
    }
}
