package org.quentin.springbootredisapp.service;

import org.quentin.springbootredisapp.dto.CommonOrder;
import org.quentin.springbootredisapp.dto.OrderItem;

import java.util.List;

public interface ServiceOfOrder {
    CommonOrder pushOrder(List<OrderItem> items) throws Exception;

    CommonOrder pushOrder(OrderItem item) throws Exception;

    List<CommonOrder> fetchOrders();

    CommonOrder updateOrder(CommonOrder order);

}
