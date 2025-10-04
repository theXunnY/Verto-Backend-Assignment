package com.verto.backendtask.controller;

import com.verto.backendtask.dto.ProductRequestDto;
import com.verto.backendtask.dto.ProductResponseDto;
import com.verto.backendtask.mapper.ProductMapper;
import com.verto.backendtask.model.Product;
import com.verto.backendtask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> list = service.getAll().stream()
                .map(ProductMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto dto){
        System.out.println("Dto : " + dto);
        Product saved = service.createProduct(ProductMapper.toEntity(dto));
        System.out.println("Saved : " + saved);
        return ResponseEntity.ok(ProductMapper.toResponseDto(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProduct(@PathVariable long id){
        Product product = service.findProduct(id);
        ProductResponseDto responseDto = ProductMapper.toResponseDto(product);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long id, @RequestBody ProductRequestDto requestDto){
        Product updatedProduct = service.updateProduct(id, ProductMapper.toEntity(requestDto));
        ProductResponseDto responseDto = ProductMapper.toResponseDto(updatedProduct);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<ProductResponseDto> purchaseStock(@PathVariable Long id, @RequestParam int stockAmount){
            Product product = service.purchaseStock(id, stockAmount);
            ProductResponseDto responseDto = ProductMapper.toResponseDto(product);

            return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{id}/sell")
    public ResponseEntity<ProductResponseDto> sellStock(@PathVariable Long id, @RequestParam int stockAmount){
        Product product = service.sellStock(id, stockAmount);
        ProductResponseDto responseDto = ProductMapper.toResponseDto(product);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/low-stocks")
    public ResponseEntity<List<ProductResponseDto>> getLowStockProduct(){
        List<Product> productList = service.findLowStockProducts();
        List<ProductResponseDto> responseDtos = productList
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(responseDtos);

    }


}
