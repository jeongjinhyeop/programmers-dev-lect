package org.example.springtheory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//자기 자신보다 하위의 컴포넌트를 스킨함 저기 어노테이션에 컴포넌트스캔들어있음
@SpringBootApplication
public class SpringTheoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTheoryApplication.class, args);
    }

}
