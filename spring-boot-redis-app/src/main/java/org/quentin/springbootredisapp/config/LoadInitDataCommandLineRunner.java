package org.quentin.springbootredisapp.config;

import org.quentin.springbootredisapp.dto.CommonOrder;
import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.dto.OrderItem;
import org.quentin.springbootredisapp.dto.ProductRepoRecord;
import org.quentin.springbootredisapp.service.ServiceOfOrder;
import org.quentin.springbootredisapp.service.ServiceOfProduct;
import org.quentin.springbootredisapp.service.ServiceOfProductRepoRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadInitDataCommandLineRunner implements CommandLineRunner {
    public static final Logger LOGGER =
            LoggerFactory.getLogger(LoadInitDataCommandLineRunner.class);
    private final ServiceOfOrder orderS;
    private final ServiceOfProduct productS;
    private final ServiceOfProductRepoRecord recordS;
    @Autowired
    private CacheManager cacheManager;

    public LoadInitDataCommandLineRunner(
            ServiceOfOrder orderS,
            ServiceOfProduct productS, ServiceOfProductRepoRecord recordS) {
        this.orderS = orderS;
        this.productS = productS;
        this.recordS = recordS;
    }

    /**
     * use ServiceOfProduct instead of productRepository
     * because service can update cache stuff, but repository not
     */
    @Override
    public void run(String... args) throws Exception {
        LOGGER.warn("Application is going to load test data!!!");
        this.loadRecordAndProducts();
        LOGGER.info("load product completely");
        try {
            this.loadOrders();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("load order completely");
        doTest();
    }

    private void doTest() {
//        Collection<String> cacheNames = cacheManager.getCacheNames();
    }

    private void loadOrders() {

        List<OrderItem> items = new ArrayList<>();
        // this idiot operate which get product id with product then give this product to record's product
        // only in this init method, production won't do like this.
        // production will only create an item with product id
        items.add(OrderItem.ItemBuilder.anOrderItem()
                .amount(1)
                .product(CommonProduct.ProductBuilder.aCommonProduct()
                        .productId(productS.fetchProduct(CommonProduct.ProductBuilder.aCommonProduct()
                                .name("iphone 13")
                                .brand("apple")
                                .build()).getProductId())
                        .build())
                .build());
        items.add(OrderItem.ItemBuilder.anOrderItem()
                .amount(1)
                .product(CommonProduct.ProductBuilder.aCommonProduct()
                        .productId(productS.fetchProduct(CommonProduct.ProductBuilder.aCommonProduct()
                                .name("iphone 13 pro")
                                .brand("apple")
                                .build()).getProductId())
                        .build())
                .build());
        try {
            CommonOrder order = orderS.pushOrder(items);
            LOGGER.info("saved order which id is {}", order.getOrderId());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void loadRecordAndProducts() {
        List<ProductRepoRecord> records = new ArrayList<>();
        records.add(ProductRepoRecord.RecordBuilder.aProductRepoRecord()
                .amount(1)
                .product(CommonProduct.ProductBuilder.aCommonProduct()
                        .brand("apple")
                        .info("electronic product")
                        .name("iphone 13")
                        .price(6000D)
                        .build())
                .build());
        records.add(ProductRepoRecord.RecordBuilder.aProductRepoRecord()
                .amount(2)
                .product(CommonProduct.ProductBuilder.aCommonProduct()
                        .brand("apple")
                        .info("electronic product")
                        .name("iphone 13 pro")
                        .price(8000D)
                        .build())
                .build());
        records.add(ProductRepoRecord.RecordBuilder.aProductRepoRecord()
                .amount(5)
                .product(CommonProduct.ProductBuilder.aCommonProduct()
                        .brand("apple")
                        .info("electronic product")
                        .name("iphone 13 pro max")
                        .price(10000D)
                        .build())
                .build());
        records.forEach(record -> {
            try {
                LOGGER.info("The repository's record has saved which id is {}",
                        recordS.writeRecord(record).getRecordId());
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        });
    }
}
