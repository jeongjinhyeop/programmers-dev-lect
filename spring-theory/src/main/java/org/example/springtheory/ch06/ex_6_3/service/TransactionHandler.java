package org.example.springtheory.ch06.ex_6_3.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {
    // 부가기능을 적용할 실제 오브젝트(ex: UserServiceImpl)
    private Object target;
    // 트랜잭션 추상화
    private PlatformTransactionManager transactionManager;//advice
    // 이 이름으로 '시작'하는 메서드에만 트랜잭션 적용
    private String pattern;  //pointcut

    public TransactionHandler(Object target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 호출된 메서드 '이름'이 패턴으로 시작하면 트랜잭션으로 감싼다.
        // 예) pattern = "upgrade"이면 upgradeLevels()는 매칭, add()는 비매칭
        if( method.getName().startsWith(pattern) ) {
            // 트랜잭션 경계설정
            return invokeTransaction(method, args);
        }

        // 패턴에 안맞는 메서드는 부가기능 없이 target에게 그냥 위임한다.
        //  method.invoke(target, args) = "target의 이 메서드를, 이 인자들로 실행하라"(리플렉션 호출).
        return method.invoke(target, args);
    }

    private Object invokeTransaction(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {

            Object invoke = method.invoke(target, args);
            transactionManager.commit(status);

            return invoke;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
