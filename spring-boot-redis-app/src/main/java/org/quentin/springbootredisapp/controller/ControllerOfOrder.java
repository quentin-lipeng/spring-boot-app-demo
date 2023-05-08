package org.quentin.springbootredisapp.controller;

import org.quentin.springbootredisapp.dto.CommonOrder;
import org.quentin.springbootredisapp.dto.OrderItem;
import org.quentin.springbootredisapp.service.ServiceOfOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiLocation.ORDER)
public class ControllerOfOrder {
    private static final String ALL_ORDERS = "/all";
    private static final String ORDERS = "/items";
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerOfOrder.class);
    private final ServiceOfOrder orderS;

    public ControllerOfOrder(ServiceOfOrder orderS) {
        this.orderS = orderS;
    }

    @GetMapping(ALL_ORDERS)
    public List<CommonOrder> allOrders() {
        return orderS.fetchOrders();
    }

    @PostMapping(ORDERS)
    public CommonOrder pushItems(@RequestBody List<OrderItem> items) {
        CommonOrder savedOrder;
        try {
            savedOrder = orderS.pushOrder(items);
        } catch (Exception e) {
            LOGGER.error("product error");
            return new CommonOrder();
        }
        return savedOrder;
    }

}
