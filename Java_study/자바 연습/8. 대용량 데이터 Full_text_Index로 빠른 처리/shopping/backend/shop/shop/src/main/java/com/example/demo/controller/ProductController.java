package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public Page<Product> getAllProducts(
            @PageableDefault(size = 10) Pageable pageable) {
        // DB에서 100만 개 중 딱 요청한 페이지(size 만큼)만 가져옵니다.
        return productRepository.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<Product> searchProducts(
            @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable pageable) {

        return productRepository.searchByFullText(keyword, pageable);
    }
}