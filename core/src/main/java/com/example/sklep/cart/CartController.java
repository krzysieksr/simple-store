package com.example.sklep.cart;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    //    private final CartRepository cartRepository;
    private final CartFacade cartFacade;


    @PostMapping("/{customerId}")
    public ResponseEntity<Object> createCart(@PathVariable Integer customerId) {
        //TODO Sprawdz w DB czy koszyk istnieje dla customerId
        cartFacade.createCart(customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Object> setProductAndAmount(@PathVariable Integer customerId,
                                                      @RequestBody ProductAddedToCartDTO productAddedToCartDTO) {
        cartFacade.addProductToCart(customerId, productAddedToCartDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
