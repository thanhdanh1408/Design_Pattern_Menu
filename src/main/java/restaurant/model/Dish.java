package main.java.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String name;
    private double price;
    private String description;
    private List<Ingredient> ingredients;

    public Dish(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + " - $" + price + " (" + description + ")");
        if (!ingredients.isEmpty()) {
            sb.append(", Thành phần: ");
            for (Ingredient ingredient : ingredients) {
                sb.append(ingredient.toString()).append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}