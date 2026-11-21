package com.microservice.productservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products" ,
uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_product_sku",
                columnNames = "sku"

        )
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String name;

    @Column(nullable = false,length = 50)
    private String sku;

    @Column(nullable = false,length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;



}
