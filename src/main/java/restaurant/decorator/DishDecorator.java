package main.java.restaurant.decorator;

import main.java.restaurant.model.Dish;

public abstract class DishDecorator extends Dish {
    protected Dish decoratedDish;

    public DishDecorator(Dish decoratedDish) {
        super(decoratedDish.getName(), decoratedDish.getPrice(), decoratedDish.getDescription());
        this.decoratedDish = decoratedDish;
    }

    @Override
    public String getName() {
        return decoratedDish.getName();
    }

    @Override
    public double getPrice() {
        return decoratedDish.getPrice();
    }

    @Override
    public String getDescription() {
        return decoratedDish.getDescription();
    }
}