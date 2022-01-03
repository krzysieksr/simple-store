package com.example.sklep.cart;

import com.example.sklep.product.Product;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {
    //Key: customerId
    private final Map<Integer, Cart> carts = new ConcurrentHashMap<>();

    public void setProductToCart(Product product, int amountToAdd, int customerId)
            throws CartNotFoundException {
        Cart customerCart = carts.get(customerId);
        customerCart.pushProduct(product, amountToAdd);
    }

    public Cart getCartOrThrow(int customerId) throws CartNotFoundException {
        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        return cart;
    }

    public void createCart(Integer customerId) {
        carts.putIfAbsent(customerId, new Cart());
    }
}
