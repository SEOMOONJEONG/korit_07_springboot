package com.example.cardatabase;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    // 생성자? 객체? 메서드? 일단 객체는 아님. 생성자는 return 이 없음.(class 그 자체로 컴파일 되기 때문) 그래서 생성자도 아님.
    // 메서드 라고 볼 수 있음. 새로운 OpenAPI 객체를 만들어서 반환해줌.
    @Bean
    public OpenAPI carDatabaseOpenApi() {
        return new OpenAPI()
                .info(new Info()    // Builder 패턴
                        .title("Car REST API")
                        .description("My Car Stock")
                        .version("1.0")
                );
    }
}
