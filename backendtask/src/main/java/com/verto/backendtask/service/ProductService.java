package com.verto.backendtask.service;


import com.verto.backendtask.exception.InsufficientStockException;
import com.verto.backendtask.exception.ProductNotFoundException;
import com.verto.backendtask.model.Product;
import com.verto.backendtask.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product createProduct( Product product){
        if (product.getStockQuantity() < 0){
            throw new IllegalArgumentException("Stock's cannot be negative");
        }
        return repository.save(product);
    }

    public Product findProduct(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public Product updateProduct(Long id, Product product){
        if (!repository.existsById(id)){
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }

        Product dbProduct = repository.findById(id).get();

        if (product.getName() != null) dbProduct.setName(product.getName());

        if (product.getDescription() != null) dbProduct.setDescription(product.getDescription());

        if (product.getLowStockThreshold() != dbProduct.getLowStockThreshold() && product.getLowStockThreshold() >0)
            dbProduct.setLowStockThreshold(product.getLowStockThreshold());

        if (product.getStockQuantity() != dbProduct.getStockQuantity() && product.getStockQuantity()>0)
            dbProduct.setStockQuantity(product.getStockQuantity());

        return repository.save(dbProduct);
    }

    public void  deleteProduct(long id){
        if (!repository.existsById(id)){
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        repository.deleteById(id);
    }

    public Product purchaseStock (Long productId, int purchaseQuantity){
       Product product = findProduct(productId);
        if (purchaseQuantity < 0){
            throw new IllegalArgumentException("Stock amount cannot be negative");
        }
        product.setStockQuantity(product.getStockQuantity()+purchaseQuantity);

        return repository.save(product);
    }


    public Product sellStock (Long productId, int quantity){
        Product product = findProduct(productId);
        if (quantity > product.getStockQuantity()){
            throw new InsufficientStockException("Insufficient stock amount : " + product.getStockQuantity() +"Products remaining ");
        }
        product.setStockQuantity(product.getStockQuantity()-quantity);

        return repository.save(product);
    }

    public List<Product> findLowStockProducts(){
        return repository.findAll()
                .stream()
                .filter( p -> p.getStockQuantity() < p.getLowStockThreshold())
                .toList();
    }


    public List<Product> getAll() {
        return repository.findAll();
    }
}
