package com.vutz.watercalccli.account.aop;

import com.vutz.watercalccli.account.annotation.LogLoginAction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AccountAop {

    @Around("@annotation(logAnno)")
    public Object logAccountAction(ProceedingJoinPoint joinPoint, LogLoginAction logAnno) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String action = logAnno.value();

        if(action.equals("login")){
            log.info("login([{}, {}])", args[0], args[1]);
        }
        else if(action.equals("logout")){
            log.info("logout([])");
        }

        return joinPoint.proceed();
    }

}
