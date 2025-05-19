package main.java.restaurant.factory;

import main.java.restaurant.model.Dish;

public abstract class DishFactory {
	public abstract Dish createDish(String name, double price, String description);
}