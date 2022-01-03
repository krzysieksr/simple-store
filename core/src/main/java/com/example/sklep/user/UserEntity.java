package com.example.sklep.user;

import com.example.sklep.deliveryaddress.DeliveryAddressEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "User")
public class UserEntity {

    @Id
    @GeneratedValue
    private int userId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private boolean enabled;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<DeliveryAddressEntity> deliveryAddressEntity;

    private String username;
    private String password;
    private String fullName;

    public UserEntity(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

}
