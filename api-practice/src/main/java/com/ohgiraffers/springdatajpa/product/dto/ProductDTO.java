package com.ohgiraffers.springdatajpa.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {

    private int productCode;

    private String productName;

    private String originCost;

    private String releaseDate;

    private String discountRate;

    private String salesQuantity;

    private String stockQuantity;

    private String categoryCode;

    private String productionStatus;
}
