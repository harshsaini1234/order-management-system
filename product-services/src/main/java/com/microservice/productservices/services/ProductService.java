package com.microservice.productservices.services;

import com.microservice.productservices.Repository.ProductRepository;
import com.microservice.productservices.dto.ProductRequest;
import com.microservice.productservices.dto.ProductResponse;
import com.microservice.productservices.entity.Product;
import com.microservice.productservices.exception.DuplicateProductSkuException;
import com.microservice.productservices.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForCalendar;
import org.hibernate.validator.internal.constraintvalidators.hv.NormalizedValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct (ProductRequest request) {
        String sku = normalizeSku(request.sku());
        if (productRepository.existsBySku(sku)) {
            throw new DuplicateProductSkuException("Product with SKU" + sku + "already exists");

        }
        Product product = new Product();
        product.setName(request.name().trim());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product  = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with ID "+id));
        return mapToResponse(product);

    }

    @Transactional
    public ProductResponse updateProduct(Long id,ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with ID "+id));
        String sku = normalizeSku(request.sku());
        if (productRepository.existsBySkuAndIdNot(sku,id)){
            throw  new DuplicateProductSkuException(" Product with SKU "+sku+" already exists");
        }
        product.setName(request.name().trim());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);


    }
    @Transactional
    public void deleteProduct( Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID " + id));

        productRepository.delete(product);



    }


    private String normalizeSku(String sku){
        return sku.trim().toLowerCase(Locale.ROOT);

    }
    private ProductResponse mapToResponse(Product product){
        return  new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );

    }




}
