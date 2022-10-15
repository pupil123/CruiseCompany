package domain.builder;

import domain.Order;
import domain.OrderStatus;

import java.time.LocalDate;

/**
 {@code OrderBuilder} responsible for building instance of entity class
 *
 */
public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder buildId(int orderId) {
        this.order.setId(orderId);
        return this;
    }

    public OrderBuilder buildUserId(int userId) {
        this.order.setUserId(userId);
        return this;
    }

    public OrderBuilder buildCruiseId(int cruiseId) {
        this.order.setCruiseId(cruiseId);
        return this;
    }

    public OrderBuilder buildStatus(OrderStatus status) {
        this.order.setStatus(status);
        return this;
    }

    public OrderBuilder buildDate(LocalDate date) {
        this.order.setDate(date);
        return this;
    }

    public Order build() {
        return this.order;
    }
}
