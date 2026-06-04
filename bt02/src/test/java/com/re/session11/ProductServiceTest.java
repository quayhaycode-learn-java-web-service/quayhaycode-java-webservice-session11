package com.re.session11;

import com.re.session11.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductService.ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository = mock(ProductService.ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void testUpdateStock_Success() {
        ProductService.Product product = new ProductService.Product("P1", 10);
        when(repository.findById("P1")).thenReturn(Optional.of(product));
        when(repository.save(any())).thenReturn(product);

        // Cộng (Thêm 5)
        assertThat(service.updateStock("P1", 5)).isEqualTo(15);
        verify(repository).save(product);

        // Trừ (Trừ 3)
        assertThat(service.updateStock("P1", -3)).isEqualTo(12);
        verify(repository, times(2)).save(product);
    }

    @Test
    void testUpdateStock_NegativeStockError() {
        ProductService.Product product = new ProductService.Product("P1", 10);
        when(repository.findById("P1")).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> service.updateStock("P1", -15))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void testUpdateStock_ProductNotFound() {
        when(repository.findById("P2")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateStock("P2", 5))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repository, never()).save(any());
    }
}