package com.example.sklep.acpect;

import com.example.sklep.user.requests.AuthRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMeasureAspect {

    @Around("execution(* com.example.sklep.cart.CartFacade..*(..))")
    public Object methodTimingMeasure(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long elapse = System.nanoTime() - start;

        Signature signature = joinPoint.getSignature();
        Logger logger = LogManager.getLogger(signature.getDeclaringType());
        logger.info("Time of execution of {} method with argument {} is {} ns.",
                signature.getName(), joinPoint.getArgs(), elapse);
        return proceed;
    }

    @Before("execution(* com.example.sklep.user.UserController.login(..))")
    public void beforeLoginUser(JoinPoint joinPoint) {
        Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringType());
        AuthRequest arg = (AuthRequest) joinPoint.getArgs()[0];
        logger.info("****** LOGGING IN THE USER {}*****", arg.getUsername());
    }
}
