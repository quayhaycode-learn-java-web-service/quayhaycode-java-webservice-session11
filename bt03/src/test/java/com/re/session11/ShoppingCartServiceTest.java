package com.re.session11;

import com.re.session11.entity.CartItem;
import com.re.session11.entity.Product;
import com.re.session11.entity.ShoppingCart;
import com.re.session11.repository.CartRepository;
import com.re.session11.repository.ProductRepository;
import com.re.session11.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private CartRepository cartRepository;
    @InjectMocks private ShoppingCartService cartService;
    @Captor private ArgumentCaptor<ShoppingCart> cartCaptor;

    // 1. Happy Path: Thêm mới giỏ hàng
    @Test
    void testAddProduct_NewCartCreated() {
        Product p = new Product(1L, "Laptop", 1000.0, 10);
        when(productRepository.findById(1L)).thenReturn(p);
        when(cartRepository.findByUserId(100L)).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        cartService.addProductToCart(100L, 1L, 2);

        verify(cartRepository).save(cartCaptor.capture());
        assertThat(cartCaptor.getValue().getItems()).hasSize(1);
    }

    // 2. Unhappy Path: Cập nhật sản phẩm không tồn tại trong giỏ
    @Test
    void testUpdateQuantity_ProductNotInCart_ThrowsException() {
        ShoppingCart cart = new ShoppingCart(100L);
        when(cartRepository.findByUserId(100L)).thenReturn(Optional.of(cart));

        assertThatThrownBy(() -> cartService.updateProductQuantity(100L, 99L, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // 3. Kịch bản phức tạp: Cập nhật vượt quá tồn kho mới (Race Condition)
    @Test
    void testUpdateQuantity_ExceedingAvailableStock_DueToConcurrency() {
        // Tồn kho thực tế chỉ còn 5 do người dùng khác đã mua
        Product p = new Product(1L, "Laptop", 1000.0, 5);
        ShoppingCart cart = new ShoppingCart(100L);
        cart.addItem(new CartItem(1L, 2));

        when(cartRepository.findByUserId(100L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(1L)).thenReturn(p);

        // Cố gắng cập nhật lên 7
        assertThatThrownBy(() -> cartService.updateProductQuantity(100L, 1L, 7))
                .isInstanceOf(IllegalStateException.class);
    }

    // 4. Kiểm tra hành vi khi xóa sản phẩm không tồn tại (đã bị xóa khỏi hệ thống)
//    @Test
//    void testRemoveItem_EvenIfProductMissingInDB() {
//        ShoppingCart cart = new ShoppingCart(100L);
//        cart.addItem(new CartItem(1L, 1));
//
//        when(cartRepository.findByUserId(100L)).thenReturn(Optional.of(cart));
//        // ProductRepository trả về null nhưng việc xóa khỏi giỏ vẫn phải được phép
//        when(productRepository.findById(1L)).thenReturn(null);
//
//        cartService.removeProductFromCart(100L, 1L);
//
//        verify(cartRepository).save(cartCaptor.capture());
//        assertThat(cartCaptor.getValue().getItems()).isEmpty();
//    }


}