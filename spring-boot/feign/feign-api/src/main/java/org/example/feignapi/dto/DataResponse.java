package org.example.feignapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
//@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
public class DataResponse {
    private Long id;
    private String name;
    private int value;


}
F