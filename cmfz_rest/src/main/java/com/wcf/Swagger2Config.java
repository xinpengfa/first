package com.wcf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springboot和swagger2的整合
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //apiInfo 代表api文档的信息
                .apiInfo(apiInfo())
                .select()
                 // rest controller package
                .apis(RequestHandlerSelectors.basePackage("com.wcf.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("吴超凡 Spring Boot中使用Swagger2构建RESTful APIs")
                .description("swagger2入门教程，更多请关注http://www.gaozhy.cn")
                .termsOfServiceUrl("http://www.gaozhy.cn")
                .contact("gaozhy")
                .version("1.0")
                .build();
    }
}
