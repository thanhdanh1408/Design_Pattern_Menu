package main.java.restaurant.model;

public class Invoice {
    private Order order;
    private double finalPrice;

    public Invoice(Order order) {
        this.order = order;
        this.finalPrice = order.getTotalPrice();
    }

    public void applyPromotion(Promotion promotion) {
        finalPrice = finalPrice * (1 - promotion.getDiscountRate());
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    @Override
    public String toString() {
        return "Invoice: Total = $" + finalPrice;
    }
}