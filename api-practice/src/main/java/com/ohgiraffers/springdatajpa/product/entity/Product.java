package com.ohgiraffers.springdatajpa.product.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_info")
@Getter
/* @Setter 지양 -> 객체를 언제든지 변경할 수 있으면 객체의 안전성 보장 X
*  값 변경이 필요한 경우에는 해당 기능을 위한 메소드를 별도로 생성 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* @AllArgsConstructor */
/* @ToString : 사용 가능하나 연괄 관계 매핑 필드는 제거하고 사용 */
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCode;

    private String productName;

    private String originCost;

    private String releaseDate;

    private String discountRate;

    private String salesQuantity;

    private String stockQuantity;

    private String categoryCode;

    private String productionStatus;

    public void modifyProductName(String productName) {
        this.productName = productName;
    }
}
