package com.example.sklep.deliveryaddress;

import com.example.sklep.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "DeliveryAddress")
public class DeliveryAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private String street;
    private String postCode;
    private String city;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    @JsonBackReference
    private UserEntity userEntity;
}
