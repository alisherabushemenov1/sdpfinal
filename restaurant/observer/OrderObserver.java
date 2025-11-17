package restaurant.observer;

public interface OrderObserver {
    void update(String orderId, String status);
}
