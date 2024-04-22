package com.example.rucafeapp;

import java.util.ArrayList;

/**
 * This class manages all the orders in the RU Cafe system.
 * Maintains a list of all orders placed, manages the current order,
 * and provides specific functionalities to add, remove, and retrieve specific orders.
 * Orders have a unique order number, that is incremented with each new order,
 * starting from an initial number.
 *
 * @author Ved Patel, Vivek Manthri
 */
public class OrderManager {
    private ArrayList<Order> allOrders;
    private Order currentOrder;
    private static OrderManager orderManagerInstance;

    /**
     * Default constructor/no-argument constructor.
     * Initializes an empty list for orders and creates the first order with the initial order number.
     */
    public OrderManager() {
        this.allOrders = new ArrayList<>();
        currentOrder = new Order(1);
    }

    /**
     * Returns the singleton instance of the OrderManager class
     *
     * @return the singleton instance of OrderManager
     */
    public static synchronized OrderManager getInstance() {
        if (orderManagerInstance == null) {
            orderManagerInstance = new OrderManager();
        }
        return orderManagerInstance;
    }

    /**
     * A getter method returns the current active order.
     *
     * @return the current Order object.
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Adds the current order to the list of orders.
     * Initializes a new current order and
     * increments the order number for the next order.
     */
    public void addOrder() {

        Order orderToAdd = new Order(currentOrder.getCurrentOrderNumber());
        orderToAdd.addItemToCurrent(new ArrayList<>(currentOrder.getCurrentItems()));
        allOrders.add(orderToAdd);

        int nextOrderNumber = currentOrder.getCurrentOrderNumber() + 1;
        currentOrder = new Order(nextOrderNumber);

    }

    /**
     * Removes a specified order from the list of orders.
     *
     * @param order The order to be removed.
     */
    public void removeOrder(Order order) {
        allOrders.remove(order);
    }

    /**
     * A getter method returns an order by its order number.
     *
     * @param orderIndex the unique order number index of the order to retrieve.
     * @return the Order object with the specified order index number, or null if not found.
     */
    public Order getOrderFromIndex(int orderIndex) {
        for (Order order : allOrders) {
            if (order.getCurrentOrderNumber() == orderIndex) {
                return order;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of order numbers for all orders.
     *
     * @return A list containing the order numbers of all orders.
     */
    public ArrayList<Integer> getOrderNumbers() {
        ArrayList<Integer> orderNumbers = new ArrayList<>();
        for (Order order : allOrders) {
            orderNumbers.add(order.getCurrentOrderNumber());
        }
        return orderNumbers;
    }

}

