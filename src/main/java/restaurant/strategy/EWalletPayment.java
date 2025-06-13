package main.java.restaurant.strategy;

public class EWalletPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Trả $" + amount + " sử dụng Ví điện tử.");
    }
}