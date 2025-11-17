package restaurant.strategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Payment in cash: " + amount + " KZT");
    }
}
