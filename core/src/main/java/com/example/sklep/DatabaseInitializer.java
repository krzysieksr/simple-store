package com.example.sklep;

import com.example.sklep.deliveryaddress.DeliveryAddressEntity;
import com.example.sklep.product.entites.ProductDetailsEntity;
import com.example.sklep.product.entites.ProductEntity;
import com.example.sklep.product.repository.ProductRepository;
import com.example.sklep.user.UserDto;
import com.example.sklep.user.UserEntity;
import com.example.sklep.user.UserRepository;
import com.example.sklep.user.UserService;
import com.example.sklep.user.requests.CreateUserRequest;
import com.google.common.collect.ImmutableSet;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DatabaseInitializer(UserService userService, ProductRepository productRepository,
                               UserRepository userRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        createProducts();
        createUsersWithAddresses();
    }

    @Transactional
    public void createProducts() {
        List<ProductEntity> productsList = List.of(
                ProductEntity.builder()
                        .name("Samsung S21")
                        .price(new BigDecimal("69"))
                        .productDetailsEntity(ProductDetailsEntity.builder()
                                .description("No w choj fajny telefon")
                                .imagesPaths("/parth/to/samsung/photos")
                                .build())
                        .build(),
                ProductEntity.builder()
                        .name("Pixel 4a")
                        .price(new BigDecimal("274"))
                        .productDetailsEntity(ProductDetailsEntity.builder()
                                .description("Tez fajny")
                                .imagesPaths("/parth/to/pixel/photos")
                                .build())
                        .build(),
                ProductEntity.builder()
                        .name("Ksjomi")
                        .price(new BigDecimal("666"))
                        .productDetailsEntity(ProductDetailsEntity.builder()
                                .description("Ksjajomi lepsze")
                                .imagesPaths("/parth/to/xaomi/photos")
                                .build())
                        .build(),
                ProductEntity.builder()
                        .name("Mtorola")
                        .price(new BigDecimal("1000"))
                        .productDetailsEntity(ProductDetailsEntity.builder()
                                .description("Lepiej zylo sie na Motorolach")
                                .imagesPaths("/parth/to/motorola/photos")
                                .build())
                        .build(),
                ProductEntity.builder()
                        .name("Sony")
                        .price(new BigDecimal("1400"))
                        .productDetailsEntity(ProductDetailsEntity.builder()
                                .description("Sony srony")
                                .imagesPaths("/parth/to/sony/photos")
                                .build())
                        .build(),
                ProductEntity.builder()
                        .name("Redmi")
                        .price(new BigDecimal("700"))
                        .productDetailsEntity(ProductDetailsEntity.builder()
                                .description("Redmi Note")
                                .imagesPaths("/parth/to/redmi/photos")
                                .build())
                        .build()
        );

        List<ProductEntity> productEntitiesList = productRepository.saveAll(productsList);
    }

    @Transactional
    public void createUsersWithAddresses() {
        // create user
        UserDto userDto_1 = userService.create(CreateUserRequest.builder()
                .username("krzysieksr")
                .password("dupa")
                .rePassword("dupa")
                .build());
        //Adding delivery addresses
        UserEntity userEntity_1 = userRepository.findById(userDto_1.getUserId()).get();
        ImmutableSet<DeliveryAddressEntity> addressSet_1 = ImmutableSet.of(DeliveryAddressEntity.builder()
                        .street("Zachodnia 77")
                        .postCode("32-321")
                        .city("Krakow")
                        .userEntity(userEntity_1)
                        .build(),
                DeliveryAddressEntity.builder()
                        .street("Kosciuszki 233")
                        .postCode("33-333")
                        .city("Gdansk")
                        .userEntity(userEntity_1)
                        .build(),
                DeliveryAddressEntity.builder()
                        .street("Czarnowiejska 666")
                        .postCode("66-666")
                        .city("Warszawa")
                        .userEntity(userEntity_1)
                        .build());
        userEntity_1.setDeliveryAddressEntity(addressSet_1);
        userRepository.save(userEntity_1);

        // create user
        UserDto userDto_2 = userService.create(CreateUserRequest.builder()
                .username("marcin")
                .password("chuj")
                .rePassword("chuj")
                .build());
        //Adding delivery addresses
        UserEntity userEntity_2 = userRepository.findById(userDto_2.getUserId()).get();
        ImmutableSet<DeliveryAddressEntity> addressSet_2 = ImmutableSet.of(DeliveryAddressEntity.builder()
                        .street("Florianska 22")
                        .postCode("51-512")
                        .city("Poznan")
                        .userEntity(userEntity_2)
                        .build(),
                DeliveryAddressEntity.builder()
                        .street("Kwiatowa 14")
                        .postCode("22-223")
                        .city("Wroclaw")
                        .userEntity(userEntity_2)
                        .build());
        userEntity_2.setDeliveryAddressEntity(addressSet_2);
        userRepository.save(userEntity_2);
    }

}
