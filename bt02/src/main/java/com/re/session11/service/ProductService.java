package com.re.session11.service;

import java.util.Optional;

public class ProductService {

    private final ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    public int updateStock(String productId, int quantityChange) {
//        Optional<Product> productOpt = productRepository.findById(productId);
//        if (productOpt.isEmpty()) {
//            throw new IllegalArgumentException("Product not found with ID: " + productId);
//        }
//
//        Product product = productOpt.get();
//        int currentStock = product.getStockQuantity();
//        int newStock = currentStock + quantityChange;
//
//        if (newStock < 0) {
//            // Lỗi: Cho phép tồn kho âm trong khi quy tắc nghiệp vụ cấm
//            throw new IllegalStateException("Resulting stock would be negative"); // Nên là IllegalArgumentException nếu lỗi nghiệp vụ
//        }
//
//        product.setStockQuantity(newStock);
//        // Lỗi: Quên gọi save để cập nhật thay đổi vào database
//        // productRepository.save(product);
//
//        return newStock;
//    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int updateStock(String productId, int quantityChange) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        int newStock = product.getStockQuantity() + quantityChange;

        if (newStock < 0) {
            throw new IllegalArgumentException("Resulting stock cannot be negative. Current: "
                    + product.getStockQuantity() + ", requested change: " + quantityChange);
        }

        product.setStockQuantity(newStock);
        // Đã sửa: Ghi nhận thay đổi vào database
        productRepository.save(product);

        return newStock;
    }

    // Giả định các class/interface sau tồn tại:
    public static class Product {
        private String id;
        private int stockQuantity;

        public Product(String id, int stockQuantity) {
            this.id = id;
            this.stockQuantity = stockQuantity;
        }

        public String getId() { return id; }
        public int getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    }

    public interface ProductRepository {
        Optional<Product> findById(String id);
        Product save(Product product);
    }
}