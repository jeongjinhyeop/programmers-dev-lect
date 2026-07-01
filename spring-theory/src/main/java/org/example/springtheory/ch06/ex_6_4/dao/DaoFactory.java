package org.example.springtheory.ch06.ex_6_4.dao;

import org.example.springtheory.ch06.ex_6_4.service.UserService;
import org.example.springtheory.ch06.ex_6_4.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
//  - 선언적 트랜잭션(@Transactional)을 켜는 스위치.
//  - 이 한 줄이 6.5에서 손수 등록하던 것들(자동 프록시 생성기 + 트랜잭션 Advisor 등)을
//    스프링이 내부적으로 대신 등록해준다.
//  => DaoFactory에서 Advice/Pointcut/Advisor/AutoProxyCreator 빈이 전부 사라졌다!
@EnableTransactionManagement
public class DaoFactory {

//    @Bean
//    public ProxyFactoryBean userService(){
//        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
//        proxyFactoryBean.setTarget(userServiceImpl());
//        proxyFactoryBean.addAdvisor(transactionAdvisor());
//        return proxyFactoryBean;
//    }
//
//    //Advisor = advice + pointcut
//    @Bean
//    public DefaultPointcutAdvisor transactionAdvisor() {
//        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
//    }
//
//    @Bean
//    public TransactionAdvice transactionAdvice() {
//        return new TransactionAdvice(transactionManager());
//    }
//
//    @Bean
//    public NameMatchMethodPointcut transactionPointcut(){
//        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
//        nameMatchMethodPointcut.setMappedName("upgrade*"); // 다른거 적용하려면 파라메터로 추가로 넣어주면 된다.
//        return nameMatchMethodPointcut;
//    }
    @Bean
    public UserService userService() {
    return new UserServiceImpl(userDAO());
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