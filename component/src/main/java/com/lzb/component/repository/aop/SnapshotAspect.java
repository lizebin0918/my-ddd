package com.lzb.component.repository.aop;

import java.util.Optional;

import com.lzb.component.aggregate.BaseAggregate;
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
    /*@AfterReturning(pointcut = "execution(* GetRepository.get(..)) " +
            "|| @annotation(Snapshot)",
            returning = "returnVal")*/
    public void handleRequestMethod(JoinPoint pjp, Object returnVal) {
        if (returnVal instanceof BaseAggregate) {
            ((BaseAggregate<?>) returnVal).setSnapshot();
            return;
        }
        if (returnVal instanceof Optional) {
            Optional returnValOpt = (Optional) returnVal;
            if (returnValOpt.isPresent()) {
                Object o = returnValOpt.get();
                if (o instanceof BaseAggregate) {
                    ((BaseAggregate<?>) o).setSnapshot();
                    return;
                }
            }
        }
    }

}