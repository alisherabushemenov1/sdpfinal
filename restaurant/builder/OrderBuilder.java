package restaurant.builder;

import restaurant.factory_and_absfactory.Dish;

public class OrderBuilder {
    private final Order order;
    public OrderBuilder() { this.order = new Order(); }
    public OrderBuilder addDish(Dish d) { order.addDish(d); return this; }
    public Order build() { return order; }
}
