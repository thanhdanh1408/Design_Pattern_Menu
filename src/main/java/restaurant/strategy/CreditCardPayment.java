package main.java.restaurant.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Trả $" + amount + " sử dụng Thẻ tín dụng.");
    }
}
