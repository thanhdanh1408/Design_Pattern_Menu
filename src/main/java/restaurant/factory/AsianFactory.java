package main.java.restaurant.factory;

import main.java.restaurant.model.Dish;

public class AsianFactory implements AbstractFactory {
    @Override
    public Dish createAsianDish() {
        return new Dish("Pho", 10.0, "Vietnamese noodle soup");
    }

    @Override
    public Dish createEuropeanDish() {
        return null;
    }
}
