package org.quentin.springbootredisapp.config;

import org.quentin.springbootredisapp.dao.ProductRepoRecordRepository;
import org.quentin.springbootredisapp.dao.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration(proxyBeanMethods = false)
@EnableScheduling
public class ScheduleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);

    private final ProductRepository productRep;
    private final ProductRepoRecordRepository recordRepo;

    public ScheduleConfig(ProductRepository productRep, ProductRepoRecordRepository recordRepo) {
        this.productRep = productRep;
        this.recordRepo = recordRepo;
    }

    /**
     * todo check order with record
     */
    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 3000)
    public void checkRecordAndProduct() {
        LOGGER.info("product's count is {}, record's count is {}",
                productRep.count(), recordRepo.count());
    }
}
