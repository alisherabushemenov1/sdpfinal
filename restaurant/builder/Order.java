package restaurant.builder;

import java.util.ArrayList;
import java.util.List;
import restaurant.factory_and_absfactory.Dish;
import restaurant.observer.OrderObserver;

public class Order {
    private final String orderId;
    private final List<Dish> dishes = new ArrayList<>();
    private String status;
    private final List<OrderObserver> observers = new ArrayList<>();
    private restaurant.strategy.PaymentStrategy paymentStrategy;

    public Order() {
        this.orderId = "ORD" + System.currentTimeMillis();
        this.status = "New";
    }

    public void addDish(Dish d) {
        dishes.add(d);
    }

    public List<Dish> getDishes() {
        return new ArrayList<>(dishes);
    }

    public double getTotalCost() {
        return dishes.stream().mapToDouble(Dish::getCost).sum();
    }

    public void setStatus(String s) {
        this.status = s;
        notifyObservers();
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void addObserver(OrderObserver o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (OrderObserver o : observers) {
            o.update(orderId, status);
        }
    }

    public void setPaymentStrategy(restaurant.strategy.PaymentStrategy p) {
        this.paymentStrategy = p;
    }

    public void processPayment() {
        if (paymentStrategy != null) {
            paymentStrategy.pay(getTotalCost());
        }
    }
}
