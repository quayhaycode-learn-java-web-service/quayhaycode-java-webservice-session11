package com.re.session11.service;

import com.re.session11.entity.CartItem;
import com.re.session11.entity.Product;
import com.re.session11.entity.ShoppingCart;
import com.re.session11.repository.CartRepository;
import com.re.session11.repository.ProductRepository;

import java.util.Optional;

public class ShoppingCartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ShoppingCartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * Thêm sản phẩm vào giỏ hàng của người dùng.
     * Nếu sản phẩm đã có trong giỏ, tăng số lượng.
     * Kiểm tra tồn kho sản phẩm.
     *
     * @param userId ID của người dùng.
     * @param productId ID của sản phẩm.
     * @param quantity Số lượng muốn thêm.
     * @return ShoppingCart đã cập nhật.
     * @throws IllegalArgumentException nếu sản phẩm không tồn tại.
     * @throws IllegalStateException nếu không đủ tồn kho.
     */
    public ShoppingCart addProductToCart(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> new ShoppingCart(userId));

        Optional<CartItem> existingItem = cart.findItemByProductId(productId);
        int currentQuantityInCart = existingItem.map(CartItem::getQuantity).orElse(0);
        int totalRequestedQuantity = currentQuantityInCart + quantity;

        if (product.getStock() < totalRequestedQuantity) {
            throw new IllegalStateException("Not enough stock for product: " + product.getName());
        }

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(totalRequestedQuantity);
        } else {
            cart.addItem(new CartItem(productId, quantity));
        }

        return cartRepository.save(cart);
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng.
     * @param userId ID của người dùng.
     * @param productId ID của sản phẩm.
     * @param newQuantity Số lượng mới.
     * @return ShoppingCart đã cập nhật.
     * @throws IllegalArgumentException nếu sản phẩm không tồn tại trong giỏ hoặc số lượng mới <= 0.
     * @throws IllegalStateException nếu không đủ tồn kho cho số lượng mới.
     */
    public ShoppingCart updateProductQuantity(Long userId, Long productId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        CartItem itemToUpdate = cart.findItemByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart: " + productId));

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found in database: " + productId); // Should ideally not happen if product was added
        }

        if (product.getStock() < newQuantity) {
            throw new IllegalStateException("Not enough stock for product: " + product.getName());
        }

        itemToUpdate.setQuantity(newQuantity);
        return cartRepository.save(cart);
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng.
     * @param userId ID của người dùng.
     * @param productId ID của sản phẩm.
     * @return ShoppingCart đã cập nhật.
     * @throws IllegalArgumentException nếu giỏ hàng không tồn tại hoặc sản phẩm không có trong giỏ.
     */
    public ShoppingCart removeProductFromCart(Long userId, Long productId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        CartItem itemToRemove = cart.findItemByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart: " + productId));

        cart.removeItem(itemToRemove);
        return cartRepository.save(cart);
    }
}