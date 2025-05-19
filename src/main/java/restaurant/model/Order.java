package main.java.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Dish> dishes;
    private double totalPrice;

    public Order() {
        this.dishes = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
        totalPrice += dish.getPrice();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}