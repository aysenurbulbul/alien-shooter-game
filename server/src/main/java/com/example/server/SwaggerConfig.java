package com.example.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(someInfo());
    }

    private ApiInfo someInfo(){
        Contact contact = new Contact("Ayşenur Bülbül & Zeynep Erdoğan", "http://144.122.71.144:8080/aysenur.bulbul/group21",
                "aysenur.bulbul@metu.edu.tr");
        return new ApiInfo("CENG453 Term Project Group21", " Server Side API Documentation",
                "", "", contact,
                "",
                "",
                new ArrayList<>());
    }
}
