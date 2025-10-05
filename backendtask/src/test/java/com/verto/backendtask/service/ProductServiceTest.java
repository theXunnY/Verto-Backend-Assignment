package com.verto.backendtask.service;

import com.verto.backendtask.exception.InsufficientStockException;
import com.verto.backendtask.exception.ProductNotFoundException;
import com.verto.backendtask.model.Product;
import com.verto.backendtask.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .id(1L)
                .name("Samsung")
                .description("World Best Display Phone")
                .stockQuantity(10L)
                .lowStockThreshold(5L)
                .build();
    }

    @Test
    void testCreateProduct() {
        when(repository.save(product)).thenReturn(product);
        Product created = service.createProduct(product);
        assertEquals(product.getName(), created.getName());
        assertEquals(product.getStockQuantity(), created.getStockQuantity());
    }

    @Test
    void testFindProductSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        Product found = service.findProduct(1L);
        assertEquals(product.getName(), found.getName());
    }

    @Test
    void testFindProductNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> service.findProduct(2L));
    }

    @Test
    void testSellStockSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);
        Product updated = service.sellStock(1L, 5);
        assertEquals(5L, updated.getStockQuantity());
    }

    @Test
    void testSellStockInsufficient() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        InsufficientStockException exception = assertThrows(InsufficientStockException.class,
                () -> service.sellStock(1L, 15));
        assertTrue(exception.getMessage().contains("Insufficient stock"));
    }

    @Test
    void testPurchaseStock() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);
        Product updated = service.purchaseStock(1L, 20);
        assertEquals(30L, updated.getStockQuantity());
    }

    @Test
    void testFindLowStockProducts() {
        Product lowStock = Product.builder().id(2L).name("LowStock").stockQuantity(2L).lowStockThreshold(5L).build();
        when(repository.findAll()).thenReturn(List.of(product, lowStock));
        List<Product> lowStockProducts = service.findLowStockProducts();
        assertEquals(1, lowStockProducts.size());
        assertEquals("LowStock", lowStockProducts.get(0).getName());
    }
}
