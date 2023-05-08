package org.quentin.springbootredisapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "order_item")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 749650366989423751L;
    @Id
    @GeneratedValue
    private Long itemId;
    private Integer amount;

    private Double totalPrice;
    /**
     * The order of orderItem can't in toString()
     */
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private CommonOrder order;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private CommonProduct product;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * If return OrderItem used jackson in controller directly
     * jackson will circle set property
     * because order has item has order ...
     * jackson will set property forever
     * solution1: comment this method(getOrder)
     * Because I think there is no need to get order from orderItem
     * solution2: if return order directly used jackson
     * you should set order to null
     * solution3: @JsonIgnore
     */
    @JsonIgnore
    public CommonOrder getOrder() {
        return order;
    }

    public void setOrder(CommonOrder order) {
        this.order = order;
    }

    public CommonProduct getProduct() {
        return product;
    }

    public void setProduct(CommonProduct product) {
        this.product = product;
    }

    /**
     * Do not toString order property
     * todo check does need get order from item?
     */
    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                ", product=" + product +
                '}';
    }

    public static final class ItemBuilder {
        private Long itemId;
        private Integer amount;
        private Double totalPrice;
        private CommonOrder order;
        private CommonProduct product;

        private ItemBuilder() {
        }

        public static ItemBuilder anOrderItem() {
            return new ItemBuilder();
        }

        public ItemBuilder itemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public ItemBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ItemBuilder totalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public ItemBuilder order(CommonOrder order) {
            this.order = order;
            return this;
        }

        public ItemBuilder product(CommonProduct product) {
            this.product = product;
            return this;
        }

        public OrderItem build() {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(itemId);
            orderItem.setAmount(amount);
            orderItem.setTotalPrice(totalPrice);
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            return orderItem;
        }
    }
}
