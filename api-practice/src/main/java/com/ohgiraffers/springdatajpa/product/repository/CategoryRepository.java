package com.ohgiraffers.springdatajpa.product.repository;

import com.ohgiraffers.springdatajpa.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /* findAll 메소드로 조회 가능하지만 JPQL 설정 테스트를 위해 작성함 */
//    @Query("SELECT c FROM Category c ORDER BY c.categoryCode")
    @Query(value = "SELECT CATEGORY_CODE, CATEGORY_NAME FROM product_category ORDER BY CATEGORY_CODE",
            nativeQuery = true
    )
    List<Category> findAllCategory();
}
