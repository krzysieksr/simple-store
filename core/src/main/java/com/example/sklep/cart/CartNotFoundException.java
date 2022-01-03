package com.example.sklep.cart;

public class CartNotFoundException extends IllegalStateException {
    public CartNotFoundException(Integer customerId) {
        super(String.format("Koszyk nie został utworzony dla użytkownika o id %s. Najpierw utwórz koszyk.", customerId));
    }
}
