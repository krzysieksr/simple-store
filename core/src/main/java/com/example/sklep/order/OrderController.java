package com.example.sklep.order;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderFacade orderFacade;

    @GetMapping("/{customerId}")
    public OrderDTO calculateOffer(@PathVariable Integer customerId) {
        return orderFacade.calculateOrder(customerId);
    }

    @PostMapping("/{customerId}")
    public OrderDTO makeOrder(@PathVariable Integer customerId){
        return orderFacade.makeOrder(customerId);
    }
}
