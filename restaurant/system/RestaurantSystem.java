package restaurant.system;

import java.util.ArrayList;
import java.util.List;
import restaurant.builder.Order;
import restaurant.factory_and_absfactory.Dish;

public class RestaurantSystem {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order o) {
        orders.add(o);
    }

    public void displayOrders() {
        System.out.println("\n=== All Orders ===");
        if (orders.isEmpty()) {
            System.out.println("No orders available");
            return;
        }
        for (Order order : orders) {
            System.out.println("\nOrder ID: " + order.getOrderId());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Dishes:");
            for (Dish d : order.getDishes()) {
                System.out.println("  - " + d.getDescription() + " (" + d.getCost() + " KZT)");
            }
            System.out.println("Total: " + order.getTotalCost() + " KZT");
        }
    }
}
