package com.Proyecto.La_Tertulia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Proyecto.La_Tertulia.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

     @Query("SELECT p FROM Product p WHERE p.type = :type")
    List<Product> findByType(@Param("type") String type);

    @Query("SELECT p.presentation FROM Product p WHERE p.id = :productId")
    List<String> findPresentationByProductId(@Param("productId") Long productId);

    @Query("SELECT p.price FROM Product p WHERE p.id = :productId AND p.presentation = :presentation")
    Double findPriceByProductIdAndPresentation(@Param("productId") Long productId, @Param("presentation") String presentation);
}
