package main.java.restaurant.factory;

import main.java.restaurant.model.Dish;

public class AsianFactory implements AbstractFactory {
    @Override
    public Dish createAsianDish() {
        return new Dish("Phở", 10.0, "Phở Việt Nam");
    }

    @Override
    public Dish createEuropeanDish() {
        return null;
    }
}
