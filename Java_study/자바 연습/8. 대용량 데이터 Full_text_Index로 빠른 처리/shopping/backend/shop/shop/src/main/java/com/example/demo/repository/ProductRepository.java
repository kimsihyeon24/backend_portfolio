package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    Page<Product> findByTitleContaining(String title, Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
            countQuery = "SELECT count(*) FROM products WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
            nativeQuery = true)
    Page<Product> searchByFullText(@Param("keyword") String keyword, Pageable pageable);
}
