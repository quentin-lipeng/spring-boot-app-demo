package org.quentin.springbootredisapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quentin.springbootredisapp.dao.ProductRepoRecordRepository;
import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.dto.ProductRepoRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecordRepoTest {

    @Autowired
    private ProductRepoRecordRepository recordRepo;


    @BeforeEach
    public void process() {
    }

    @Test
    public void insertRecord() {
        ProductRepoRecord record = recordRepo.save(ProductRepoRecord.RecordBuilder.aProductRepoRecord()
                .amount(1)
                .product(CommonProduct.ProductBuilder.aCommonProduct()
                        .brand("huawei")
                        .info("electricity product")
                        .name("huawei mate 50")
                        .price(8000L)
                        .build())
                .build());
        System.out.println(record);
    }
}
