package org.quentin.springbootredisapp.service.implement;

import org.quentin.springbootredisapp.dao.ProductRepoRecordRepository;
import org.quentin.springbootredisapp.dao.ProductRepository;
import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.dto.ProductRepoRecord;
import org.quentin.springbootredisapp.service.ServiceOfProductRepoRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DefaultServiceOfProductRepoRecord implements ServiceOfProductRepoRecord {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceOfProductRepoRecord.class);
    private final ProductRepoRecordRepository recordRepo;
    private final ProductRepository productRepo;

    public DefaultServiceOfProductRepoRecord(
            ProductRepoRecordRepository recordRepo,
            ProductRepository productRepo) {
        this.recordRepo = recordRepo;
        this.productRepo = productRepo;
    }

    /**
     * todo check record is valid or not
     */
    @Caching(
            evict = {@CacheEvict(cacheNames = "product", key = "#result.product.productId"),
                    @CacheEvict(cacheNames = {"products", "records"}, allEntries = true)
            }
    )
    @Override
    public ProductRepoRecord writeRecord(
            ProductRepoRecord record) {
        AtomicInteger integer = new AtomicInteger();
        integer.incrementAndGet();
        // product of record from user
        CommonProduct recordProduct = record.getProduct();
        // To check the product exist or not
        Optional<CommonProduct> productOptional = productRepo.findCommonProductByBrandAndName(recordProduct.getBrand(), recordProduct.getName());
        // not exist in repo save new product and record
        // product doesn't exist, this action may have some problem
        if (!productOptional.isPresent()) {
//            productRepo.save(record.getProduct());
            return recordRepo.save(record);
        } else {
            // The product exist
            Optional<ProductRepoRecord> recordOptional =
                    recordRepo.findByProduct(productOptional.get());
            // if record not exist, there may have some wrong thing,
            // maybe no transaction in writing record(because save product first)
            if (!recordOptional.isPresent()) {
                LOGGER.error("Something went wrong, cause product exist but record not");
                throw new RuntimeException();
            }
            ProductRepoRecord recordById = recordOptional.get();
            Integer repoRecordAmount = recordById.getAmount();
            recordById.setAmount(record.getAmount() + repoRecordAmount);
            return recordRepo.save(recordById);
        }
    }

    @Cacheable(cacheNames = "records")
    @Override
    public List<ProductRepoRecord> fetchRecords() {
        LOGGER.trace("fetch all records just called");
        return recordRepo.findAll();
    }

    @Override
    public Optional<ProductRepoRecord> fetchRecordWithProduct(CommonProduct product) {
        return recordRepo.findByProduct(product);
    }

    @CachePut(cacheNames = "records", key = "#record.recordId")
    @Override
    public ProductRepoRecord updateAmount(ProductRepoRecord record) {
        return recordRepo.save(record);
    }

    /**
     * todo use this method or drop it
     */
    @Override
    public ProductRepoRecord returnRecordIfExist(ProductRepoRecord record) throws Exception {
        Optional<ProductRepoRecord> recordOptional =
                recordRepo.findByProduct(record.getProduct());
        if (!recordOptional.isPresent()) {
            LOGGER.error("something went wrong");
            throw new IllegalAccessException();
        }
        return recordOptional.get();
    }
}
