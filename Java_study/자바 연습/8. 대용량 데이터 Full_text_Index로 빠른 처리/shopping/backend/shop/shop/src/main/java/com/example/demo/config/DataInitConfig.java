package com.example.demo.config;

import com.example.demo.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitConfig {

    @Bean
    public CommandLineRunner loadData(ProductService productService) {
        return args -> {
            System.out.println(">>>>> 외부 API로부터 상품 데이터 로딩 시작...");
            productService.createMillionProducts();
            System.out.println(">>>>> 데이터 로딩 및 저장 완료!");
        };
    }
}