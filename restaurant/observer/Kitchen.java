package restaurant.observer;

public class Kitchen implements OrderObserver {
    @Override
    public void update(String orderId, String status) {
        if ("Received".equals(status) || "Cooking".equals(status)) {
            System.out.println("[Kitchen] Order " + orderId + " - " + status);
        }
    }
}
