package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createMillionProducts() {
        if (productRepository.count() > 0) {
            log.info(">>> 이미 데이터가 존재하여 생성을 건너뜁니다. (현재 건수: {}개)", productRepository.count());
            return;
        }
        Faker faker = new Faker(new Locale("ko"));
        int totalProducts = 1_000_000;
        int batchSize = 1000; // 1,000개 단위로 묶어서 저장
        List<Product> batchList = new ArrayList<>();

        log.info(">>> 100만 개 데이터 생성 및 저장 시작...");
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= totalProducts; i++) {
            Product product = Product.builder()
                    .title(faker.commerce().productName() + " " + i) // 중복 방지를 위해 인덱스 추가
                    .price(Double.parseDouble(faker.commerce().price().replaceAll("[^\\d.]", "")))
                    .description(faker.lorem().sentence())
                    .category(faker.commerce().department())
                    .image("https://picsum.photos/200?random=" + i)
                    .build();

            batchList.add(product);

            // batchSize만큼 쌓이면 DB에 저장하고 리스트 비우기
            if (i % batchSize == 0) {
                productRepository.saveAll(batchList);
                batchList.clear();
                log.info("{}개 데이터 저장 완료...", i);
            }
        }

        long endTime = System.currentTimeMillis();
        log.info(">>> 총 소요 시간: {}초", (endTime - startTime) / 1000.0);
    }
}