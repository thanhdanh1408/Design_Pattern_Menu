package main.java.restaurant.decorator;

import main.java.restaurant.model.Dish;

public class AddOnDecorator extends DishDecorator {
    private String addOn;
    private double addOnPrice;

    public AddOnDecorator(Dish decoratedDish, String addOn, double addOnPrice) {
        super(decoratedDish);
        this.addOn = addOn;
        this.addOnPrice = addOnPrice;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + addOnPrice;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", với " + addOn;
    }
}
