package com.example.sklep.order;

import com.example.sklep.cart.Cart;
import com.example.sklep.cart.CartFacade;
import com.example.sklep.cart.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {
    private final OrderService orderService;
    private final CartFacade cartFacade;

    public OrderFacade(OrderService orderService, CartFacade cartFacade) {
        this.orderService = orderService;
        this.cartFacade = cartFacade;
    }
    
    public OrderDTO calculateOrder(int customerId) {
        Cart cardForCustomer = cartFacade.getCartOrThrow(customerId);
        return orderService.calculateOrder(cardForCustomer);
    }

    public OrderDTO makeOrder(Integer customerId) {
        Cart cardForCustomer = cartFacade.getCartOrThrow(customerId);
        return orderService.makeOrder(cardForCustomer);
    }
}
