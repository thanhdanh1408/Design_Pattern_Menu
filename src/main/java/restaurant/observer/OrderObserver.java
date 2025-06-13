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
        System.out.println("Đã cập nhật đơn hàng. Tổng số đơn hàng: " + orderManager.getOrders().size());
    }
}