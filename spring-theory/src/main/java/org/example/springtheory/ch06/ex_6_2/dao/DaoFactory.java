package org.example.springtheory.ch06.ex_6_2.dao;

import com.zaxxer.hikari.pool.ProxyFactory;
import org.example.springtheory.ch06.ex_6_2.service.TransactionAdvice;
import org.example.springtheory.ch06.ex_6_2.service.TransactionHandler;
import org.example.springtheory.ch06.ex_6_2.service.UserService;
import org.example.springtheory.ch06.ex_6_2.service.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

// DaoFactory를 스프링 빈 팩토리가 사용할 수 있는 설정정보로 리팩토링
@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {
    // UserService 빈 = ProxyFactoryBean이 생산하는 프록시
    //  - target과 advisor만 등록하면, 스프링이 프록시를 알아서 만들어준다.
    //  - 여러 advisor를 addAdvisor로 얹을 수도 있다(부가기능 여러 개 조합).
    @Bean
    public ProxyFactoryBean userService(){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userServiceImpl());
        proxyFactoryBean.addAdvisor(transactionAdvisor());
        return proxyFactoryBean;
    }

    //Advisor = advice + pointcut
    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut(){
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedName("upgrade*"); // 다른거 적용하려면 파라메터로 추가로 넣어주면 된다.
        return nameMatchMethodPointcut;
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDAO());
    }

    @Bean // 오브젝트 생성을 담당하는 IoC용 메서드라는 표시
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }

    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        return dataSource;
    }

    // * 트랜잭션 추상화의 '실제 구현'을 여기서 결정한다.
    //  - 반환 타입은 추상화 인터페이스(PlatformTransactionManager).
    //  - JDBC를 쓰므로 DataSourceTransactionManager를 꽂는다.
    //    (JPA면 JpaTransactionManager, 분산이면 JtaTransactionManager로 '이 한 줄만' 바꾸면 된다.
    //     UserService 코드는 전혀 손대지 않는다 -> 이것이 추상화의 이득)
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}