package main.java.restaurant.observer;

import  main.java.restaurant.manager.OrderManager;

public class OrderObserver implements Observer {
    private OrderManager orderManager;

    public OrderObserver(OrderManager orderManager) {
        this.orderManager = orderManager;
        orderManager.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Order updated. Total orders: " + orderManager.getOrders().size());
    }
}