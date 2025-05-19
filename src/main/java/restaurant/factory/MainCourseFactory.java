package main.java.restaurant.factory;

import main.java.restaurant.model.Dish;

public class MainCourseFactory extends DishFactory {
    @Override
    public Dish createDish(String name, double price, String description) {
        return new Dish(name, price, "Main Course: " + description);
    }
}
