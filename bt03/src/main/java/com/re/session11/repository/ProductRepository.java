package com.re.session11.repository;

//import com.re.session11.entity.Product;
import com.re.session11.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository {
    Product findById(Long id);
    void save(Product product); // Giả định cập nhật stock
}
