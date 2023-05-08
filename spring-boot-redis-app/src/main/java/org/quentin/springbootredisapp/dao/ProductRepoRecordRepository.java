package org.quentin.springbootredisapp.dao;

import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.dto.ProductRepoRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepoRecordRepository extends JpaRepository<ProductRepoRecord, Long> {
    Optional<ProductRepoRecord> findByProduct(CommonProduct product);
}
