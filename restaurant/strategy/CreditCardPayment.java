package restaurant.strategy;

public class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment of " + amount + " KZT by card " + maskCardNumber(cardNumber));
    }

    private String maskCardNumber(String number) {
        if (number != null && number.length() >= 4) {
            return "**** **** **** " + number.substring(number.length() - 4);
        }
        return number;
    }
}
