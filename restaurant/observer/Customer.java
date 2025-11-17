package restaurant.observer;

public class Customer implements OrderObserver {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String orderId, String status) {
        System.out.println("[Customer " + name + "] Order " + orderId + " - Status: " + status);
    }
}
