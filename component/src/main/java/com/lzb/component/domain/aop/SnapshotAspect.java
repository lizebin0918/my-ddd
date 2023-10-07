package com.lzb.component.domain.aop;

import java.util.Optional;

import com.lzb.component.domain.aggregate.BaseAggregate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SnapshotAspect {

    /**
     * 支持方法 or 注解
     * @param pjp
     * @param returnVal
     */
    @AfterReturning(pointcut = "execution(* com.lzb.component.domain.repository.GetRepository.get(..)) " +
            "|| @annotation(com.lzb.component.domain.annotation.Snapshot)",
            returning = "returnVal")
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        if (returnVal instanceof BaseAggregate<?> aggregate) {
            aggregate.attachSnapshot();
            return;
        }
        if (returnVal instanceof Optional<?> returnValOpt
                && returnValOpt.isPresent()
                && returnValOpt.get() instanceof BaseAggregate<?> aggregate) {
            aggregate.attachSnapshot();
        }
    }

}