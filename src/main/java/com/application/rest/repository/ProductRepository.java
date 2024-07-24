package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2") //Seleccina el producto que esta entre el primer argumento y el segundo
    List<Product> findProductByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);

    //Esta sentencia hace lo mismo que la de arriba implementando query methods
    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
