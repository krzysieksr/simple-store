package com.example.common;

public class TooFewProductAvailableException extends RuntimeException {
    public TooFewProductAvailableException(int availableProductAmount, int amountOfProduct) {
        super(String.format("Za mało produktów w magazynie. Zażądano %s, na stanie %s", amountOfProduct, availableProductAmount));
    }
}
