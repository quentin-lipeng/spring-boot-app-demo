package org.quentin.springbootredisapp.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CommonOrder implements Serializable {
    private static final long serialVersionUID = -3764812915174488299L;
    @Id
    @GeneratedValue
    private Long orderId;
    /**
     * EAGER means need all the items needed load immediately
     * The LAZY in the opposite way
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CommonOrder{" +
                "orderId=" + orderId +
                ", items=" + items +
                '}';
    }

}
