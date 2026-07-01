package org.example.springtheory.ch06.aopEx;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class PerformanceMonitorAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getDeclaringClass().getSimpleName()
                + "." + invocation.getMethod().getName();
        long start = System.nanoTime();
        try {
            return invocation.proceed();            // ★ 실제 target 메서드 실행
        } finally {
            long ms = (System.nanoTime() - start) / 1_00_000;
            System.out.printf("[PERF] %s : %dms%n", name, ms);
        }
    }
}