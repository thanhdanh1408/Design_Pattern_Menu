package main.java.restaurant.factory;

import main.java.restaurant.model.Dish;

public class EuropeanFactory implements AbstractFactory {
    @Override
    public Dish createAsianDish() {
        return null;
    }

    @Override
    public Dish createEuropeanDish() {
        return new Dish("Spaghetti", 12.0, "Italian pasta with tomato sauce");
    }
}
