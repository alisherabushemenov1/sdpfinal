package restaurant.strategy;

public class EWalletPayment implements PaymentStrategy {
    private final String email;

    public EWalletPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment via e-wallet " + email + ": " + amount + " KZT");
    }
}
