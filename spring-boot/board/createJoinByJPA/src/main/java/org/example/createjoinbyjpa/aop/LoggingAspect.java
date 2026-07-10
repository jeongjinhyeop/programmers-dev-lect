package org.example.createjoinbyjpa.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* org.example.createjoinbyjpa.controller..*(..))")
    public void controllerLayer() {}

    @Around("controllerLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().toShortString(); // 예: BoardApiController.getBoardList(..)
        long start = System.currentTimeMillis();

        System.out.println("===> 시작: " + name);
        try {
            Object result = joinPoint.proceed();  // ★ 진짜 컨트롤러 메서드 실행
            return result;                         // 반환값을 그대로 돌려줘야 정상 동작
        } finally {
            long took = System.currentTimeMillis() - start;
            System.out.println("<=== 종료: " + name + " (" + took + "ms)");
        }
    }
}
