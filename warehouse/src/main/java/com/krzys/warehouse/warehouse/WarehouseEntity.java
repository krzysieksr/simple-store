package com.krzys.warehouse.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class WarehouseEntity {

    @Id
    private String warehouseId;

    //    TODO ProductEntity?
    private Integer productId;

//    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private ProductEntity productEntity;

    private Integer amount;

}
