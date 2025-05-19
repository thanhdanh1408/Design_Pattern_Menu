package main.java.restaurant.model;

public class Promotion {
    private String name;
    private double discountRate;

    public Promotion(String name, double discountRate) {
        this.name = name;
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    @Override
    public String toString() {
        return name + " (" + (discountRate * 100) + "% off)";
    }
}