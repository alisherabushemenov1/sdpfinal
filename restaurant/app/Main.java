package restaurant.app;

import java.util.Scanner;
import restaurant.factory_and_absfactory.*;
import restaurant.decorator.*;
import restaurant.observer.*;
import restaurant.builder.*;
import restaurant.strategy.*;
import restaurant.system.RestaurantSystem;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantSystem restaurant = new RestaurantSystem();

        // observers (will be added to each order)
        Customer customer = new Customer("John");
        Kitchen kitchen = new Kitchen();
        DeliveryService delivery = new DeliveryService();

        while (true) {
            System.out.println("\n1. Create a new order");
            System.out.println("2. View all orders");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, 1, 3);

            if (choice == 1) {
                createOrder(scanner, restaurant, customer, kitchen, delivery);
            } else if (choice == 2) {
                restaurant.displayOrders();
            } else {
                System.out.println("Thank you for using the system!");
                break;
            }
        }
        scanner.close();
    }

    private static void createOrder(Scanner scanner, RestaurantSystem restaurant,
                                    Customer customer, Kitchen kitchen, DeliveryService delivery) {
        System.out.println("\n=== Creating a new order ===");

        // Choose cuisine
        System.out.println("\nChoose a cuisine:");
        System.out.println("1. Italian");
        System.out.println("2. Japanese");
        System.out.print("Your choice: ");
        int cuisineChoice = readInt(scanner, 1, 2);

        RestaurantFactory factory = (cuisineChoice == 1)
                ? new ItalianRestaurantFactory()
                : new JapaneseRestaurantFactory();

        OrderBuilder builder = new OrderBuilder();

        boolean ordering = true;
        while (ordering) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Add main dish");
            System.out.println("2. Add drink");
            System.out.println("3. Add dessert");
            System.out.println("4. Finish order");
            System.out.print("Choose: ");
            int menuChoice = readInt(scanner, 1, 4);

            if (menuChoice == 1) {
                // Show specific options depending on cuisine
                if (cuisineChoice == 1) { // Italian
                    System.out.println("\nItalian main dishes:");
                    System.out.println("1. Margherita");
                    System.out.println("2. Pepperoni");
                    System.out.println("3. Four Cheese");
                    System.out.print("Choose: ");
                    int m = readInt(scanner, 1, 3);
                    switch (m) {
                        case 1 -> builder.addDish(new Pizza("Margherita"));
                        case 2 -> builder.addDish(new Pizza("Pepperoni"));
                        default -> builder.addDish(new Pizza("Four Cheese"));
                    }
                } else { // Japanese
                    System.out.println("\nJapanese main dishes:");
                    System.out.println("1. Classic Sushi Set");
                    System.out.println("2. Classic Ramen");
                    System.out.print("Choose: ");
                    int m = readInt(scanner, 1, 2);
                    if (m == 1) builder.addDish(new Sushi("Classic Sushi Set"));
                    else builder.addDish(new Ramen("Classic Ramen"));
                }

                // Add toppings
                System.out.println("\nAdd toppings?");
                System.out.println("1. Cheese (+50 KZT)");
                System.out.println("2. Spicy Sauce (+30 KZT)");
                System.out.println("3. Both");
                System.out.println("4. None");
                System.out.print("Choice: ");
                int toppingChoice = readInt(scanner, 1, 4);

                Order temp = builder.build();
                java.util.List<restaurant.factory_and_absfactory.Dish> list = temp.getDishes();
                restaurant.factory_and_absfactory.Dish last = list.get(list.size() - 1);

                restaurant.factory_and_absfactory.Dish wrapped = last;
                if (toppingChoice == 1 || toppingChoice == 3) wrapped = new CheeseTopping(wrapped);
                if (toppingChoice == 2 || toppingChoice == 3) wrapped = new SpicySauceTopping(wrapped);

                // Replace last dish with wrapped version
                OrderBuilder newBuilder = new OrderBuilder();
                for (int i = 0; i < list.size() - 1; i++) newBuilder.addDish(list.get(i));
                newBuilder.addDish(wrapped);
                builder = newBuilder;

                System.out.println("Added: " + wrapped.getDescription() + " - " + wrapped.getCost() + " KZT");

            } else if (menuChoice == 2) {
                // Drinks
                System.out.println("\nDrinks:");
                if (cuisineChoice == 1) {
                    System.out.println("1. Red wine");
                    System.out.println("2. White wine");
                    System.out.print("Choose: ");
                    int d = readInt(scanner, 1, 2);
                    builder.addDish(new Wine(d == 1 ? "Red wine" : "White wine"));
                } else {
                    System.out.println("1. Sake");
                    System.out.println("2. Green tea");
                    System.out.print("Choose: ");
                    int d = readInt(scanner, 1, 2);
                    if (d == 1) builder.addDish(new Sake("Sake"));
                    else builder.addDish(new Wine("Green tea (non-alcoholic)")); // reuse Wine class for simple drink
                }

                restaurant.factory_and_absfactory.Dish last = builder.build().getDishes().get(builder.build().getDishes().size() - 1);
                System.out.println("Added: " + last.getDescription() + " - " + last.getCost() + " KZT");

            } else if (menuChoice == 3) {
                // Desserts
                if (cuisineChoice == 1) builder.addDish(new Tiramisu());
                else builder.addDish(new Mochi());
                restaurant.factory_and_absfactory.Dish last = builder.build().getDishes().get(builder.build().getDishes().size() - 1);
                System.out.println("Added: " + last.getDescription() + " - " + last.getCost() + " KZT");
            } else {
                ordering = false;
            }
        }

        // Build final order
        Order order = builder.build();

        // Attach observers
        order.addObserver(customer);
        order.addObserver(kitchen);
        order.addObserver(delivery);

        // Payment
        System.out.println("\n=== Payment ===");
        System.out.println("Total: " + order.getTotalCost() + " KZT");
        System.out.println("\nChoose a payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. E-Wallet");
        System.out.print("Choice: ");
        int paymentChoice = readInt(scanner, 1, 3);

        PaymentStrategy payment;
        if (paymentChoice == 1) {
            System.out.print("Card number: ");
            String card = scanner.nextLine().trim();
            payment = new CreditCardPayment(card);
        } else if (paymentChoice == 2) {
            payment = new CashPayment();
        } else {
            System.out.print("E-wallet email: ");
            String email = scanner.nextLine().trim();
            payment = new EWalletPayment(email);
        }

        order.setPaymentStrategy(payment);
        order.processPayment();

        // Update statuses
        order.setStatus("Received");
        order.setStatus("Cooking");
        order.setStatus("Ready");
        order.setStatus("Delivering");
        order.setStatus("Delivered");

        restaurant.addOrder(order);
        System.out.println("\n✓ Order successfully created! ID: " + order.getOrderId());
    }

    private static int readInt(Scanner scanner, int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int v = scanner.nextInt();
                scanner.nextLine();
                if (v >= min && v <= max) return v;
                System.out.print("Enter a number from " + min + " to " + max + ": ");
            } else {
                scanner.next(); // consume invalid token
                System.out.print("Please enter a number: ");
            }
        }
    }
}
