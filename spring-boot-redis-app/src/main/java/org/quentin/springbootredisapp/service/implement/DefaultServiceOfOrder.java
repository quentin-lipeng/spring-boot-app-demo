package org.quentin.springbootredisapp.service.implement;

import org.quentin.springbootredisapp.dao.OrderRepository;
import org.quentin.springbootredisapp.dao.ProductRepoRecordRepository;
import org.quentin.springbootredisapp.dto.CommonOrder;
import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.dto.OrderItem;
import org.quentin.springbootredisapp.dto.ProductRepoRecord;
import org.quentin.springbootredisapp.service.ServiceOfOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * todo persist error log to db use mq.
 * todo check is order cache good or not
 * The orders needn't to be cached, i think.
 */
@Service
public class DefaultServiceOfOrder implements ServiceOfOrder {

    public static final Logger LOGGER =
            LoggerFactory.getLogger(DefaultServiceOfOrder.class);
    private final OrderRepository orderRepo;
    private final ProductRepoRecordRepository recordRepo;

    public DefaultServiceOfOrder(
            OrderRepository orderRepo,
            ProductRepoRecordRepository recordRepo) {
        this.orderRepo = orderRepo;
        this.recordRepo = recordRepo;
    }

    /**
     * <p>Creat an order, which the items can join to.
     * Use @Transactional if there have multiple repository calls
     * but transactional occurred when throws an unchecked exception like runtimeException
     * use rollbackFor if you want rollback in checked exception
     * </p>
     * <a href="https://reflectoring.io/spring-transactions-and-exceptions/">this link</a>
     */
    @CacheEvict(cacheNames = "records", allEntries = true, condition = "#result.orderId!=null")
    @Transactional
    @Override
    public CommonOrder pushOrder(List<OrderItem> items) {
        LOGGER.trace("Push order with items just called");
        CommonOrder order = new CommonOrder();
        items.forEach(item -> {
            CommonProduct product = item.getProduct();
            Optional<ProductRepoRecord> recordOptional = recordRepo.findByProduct(product);
            if (!recordOptional.isPresent()) {
                LOGGER.error("There haven't record of product, maybe occurred from write record");
                throw new RuntimeException();
            }
            ProductRepoRecord repoRecord = recordOptional.get();
            Integer recordAmount = repoRecord.getAmount();
            if (item.getAmount() > recordAmount) {
                LOGGER.error("required amount bigger than repo amount");
                throw new IllegalArgumentException();
            }
            repoRecord.setAmount(recordAmount - item.getAmount());
            recordRepo.save(repoRecord);
            item.setProduct(repoRecord.getProduct());
            item.setOrder(order);
            item.setTotalPrice(item.getAmount() * repoRecord.getProduct().getPrice());
        });
        order.setItems(items);
        return orderRepo.save(order);
    }

    @Override
    public CommonOrder pushOrder(OrderItem item) throws Exception {
        return pushOrder(Collections.singletonList(item));
    }

    /**
     * Obtain all order
     * todo design obtain order with page.
     */
    @Override
    public List<CommonOrder> fetchOrders() {
        LOGGER.trace("Fetch all orders just called!");
        return orderRepo.findAll();
    }

    /**
     * todo redesign order
     * 1. never delete order, to set order status invalid then add new one.
     * 2. order status
     */
    @Override
    public CommonOrder updateOrder(CommonOrder order) {
        return null;
    }

}
