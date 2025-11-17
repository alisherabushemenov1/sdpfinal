package restaurant.observer;

public class DeliveryService implements OrderObserver {
    @Override
    public void update(String orderId, String status) {
        if ("Ready".equals(status) || "Delivering".equals(status) || "Delivered".equals(status)) {
            System.out.println("[Delivery] Order " + orderId + " - " + status);
        }
    }
}
