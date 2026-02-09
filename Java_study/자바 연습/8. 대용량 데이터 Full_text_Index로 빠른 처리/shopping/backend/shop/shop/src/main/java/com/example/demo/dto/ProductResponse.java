package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;          // API에서 주는 ID
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}