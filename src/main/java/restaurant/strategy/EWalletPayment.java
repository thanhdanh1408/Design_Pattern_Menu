package main.java.restaurant.strategy;

public class EWalletPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using E-Wallet.");
    }
}