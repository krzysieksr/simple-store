//package com.example.sklep.cart;
//
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//public class CartControllerIT {
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Before
//    public void setup() {
//        // https://rtmccormick.com/2017/07/30/solved-testing-patch-spring-boot-testresttemplate/
//        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
//    }
//
//    @Test
//    public void beforeCartCreated_cannotPushProducts() {
//        // when
//        ResponseEntity objectResponseEntity1 = testRestTemplate.patchForObject("/cart/1", ProductAddedToCartDTO.builder().productId(1).amount(5).build(), ResponseEntity.class);
//
//        assertThat(objectResponseEntity1 != null);
//
//        // then
////        assertThat(objectResponseEntity1.getStatusCodeValue()).isEqualTo(400);
//    }
//
//    @Test
//    public void afterCartCreated_canPushProducts() {
//        // given
//        testRestTemplate.postForEntity("/cart/1", null, null);
//
//        // when
//        ResponseEntity objectResponseEntity1 = testRestTemplate
//                .patchForObject("/cart/1", ProductAddedToCartDTO.builder().productId(1).amount(5).build(), ResponseEntity.class);
//
//        // then
//        assertThat(objectResponseEntity1.getStatusCodeValue()).isEqualTo(200);
//    }
//}
