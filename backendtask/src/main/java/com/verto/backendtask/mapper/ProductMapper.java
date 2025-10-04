package com.verto.backendtask.mapper;

import com.verto.backendtask.dto.ProductRequestDto;
import com.verto.backendtask.dto.ProductResponseDto;
import com.verto.backendtask.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
        product.setLowStockThreshold(dto.getLowStockThreshold());
        return product;
    }

    public static ProductResponseDto toResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setStockQuantity(product.getStockQuantity() );
        dto.setLowStockThreshold(product.getLowStockThreshold());
        return dto;
    }
}
