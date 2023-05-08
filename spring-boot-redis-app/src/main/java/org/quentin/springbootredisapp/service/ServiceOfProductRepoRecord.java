package org.quentin.springbootredisapp.service;

import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.dto.ProductRepoRecord;

import java.util.List;
import java.util.Optional;

public interface ServiceOfProductRepoRecord {
    ProductRepoRecord writeRecord(ProductRepoRecord record) throws Exception;

    List<ProductRepoRecord> fetchRecords();

    Optional<ProductRepoRecord> fetchRecordWithProduct(CommonProduct product);

    ProductRepoRecord updateAmount(ProductRepoRecord record);

    ProductRepoRecord returnRecordIfExist(ProductRepoRecord record) throws Exception;
}
