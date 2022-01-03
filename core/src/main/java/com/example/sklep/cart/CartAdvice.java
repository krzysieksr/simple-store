package com.example.sklep.cart;

import com.example.common.ProductNotFoundException;
import com.example.common.TooFewProductAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CartAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundExceptionHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TooFewProductAvailableException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String tooFewProductAvailableExceptionHandler(TooFewProductAvailableException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String cartNotFoundExceptionHandler(CartNotFoundException ex) {
        return ex.getMessage();
    }
}
