package com.microservice.productservices.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 150,message = "Product name can't exceed 150 character")
        String name,

        @NotBlank(message = "SKU is required")
        @Size(max = 50,message = "SKU can't exceed 50 character")
        String sku,

        @NotBlank(message = "Description is required")
        @Size(max = 1000,message = "Description can't exceed 50 character")
        String description,

        @NotNull(message = "Price is required" )
        @DecimalMin(value = "0.01",message = "Price must be greater than zero")
        @Digits(integer = 10,fraction = 2,message = "Price must have up to 10 integer and 2 decimal places")
        BigDecimal price,

        @NotNull(message = "StockQuantity is required")
        @PositiveOrZero(message = "Stock quantity can't be zero")
        Integer stockQuantity
) {
}
