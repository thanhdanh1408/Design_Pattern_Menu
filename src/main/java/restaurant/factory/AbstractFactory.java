package main.java.restaurant.factory;

import main.java.restaurant.model.Dish;

public interface AbstractFactory {
    Dish createAsianDish();
    Dish createEuropeanDish();
}