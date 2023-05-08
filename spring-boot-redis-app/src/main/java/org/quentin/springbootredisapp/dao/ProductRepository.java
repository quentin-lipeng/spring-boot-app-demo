package org.quentin.springbootredisapp.dao;

import org.quentin.springbootredisapp.dto.CommonProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<CommonProduct, Long> {
    Optional<CommonProduct> findCommonProductByBrandAndName(String brand, String name);
    List<CommonProduct> findCommonProductsByBrand(String brand);

    @Query("SELECT brand FROM product")
    Set<String> findAllBrands();

    @Query("SELECT productId FROM product")
    Set<Long> findAllProductIds();
}
