package com.verto.backendtask.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDto {

    private String name;
    private String description;
    private Long stockQuantity;

    private Long lowStockThreshold;
}
