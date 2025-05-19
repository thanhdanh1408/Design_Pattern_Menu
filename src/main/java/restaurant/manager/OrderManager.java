package main.java.restaurant.manager;

import main.java.restaurant.model.Order;
import main.java.restaurant.observer.Subject;
import java.util.ArrayList;
import java.util.List;

public class OrderManager extends Subject {
    private static OrderManager instance;
    private List<Order> orders;

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
        notifyObservers();
    }

    public List<Order> getOrders() {
        return orders;
    }
}
