package main.java.restaurant.strategy;

public class CashPayment implements PaymentStrategy{
	@Override
    public void pay(double amount) {
        System.out.println("Trả $" + amount + " sử dụng Tiền mặt.");
    }
}
