package com.example.rucafeapp;


import java.util.ArrayList;

/**
 * This class represents an order in the RU Cafe application.
 * Manages the list of menu items added to a particular order,
 * calculates the subtotal, sales tax, add/removal of menu items, and total amount due for the order.
 * Orders are identified by a unique order number.
 *
 * @author Ved Patel, Vivek Manthri
 */
public class Order {
    private ArrayList<MenuItem> currentItems = new ArrayList<>();
    private static final double SALES_TAX = 0.06625;
    private int currentOrderNumber;

    /**
     * Parameterized constructor requires 1 parameter to create an Order object.
     * Initializes the list of current items in the order.
     *
     * @param currentOrderNumber a unique order number for the order
     */
    public Order(int currentOrderNumber) {
        this.currentOrderNumber = currentOrderNumber;
    }

    /**
     * A getter method returns the observable list of menu items currently in the order.
     *
     * @return an observable list of MenuItem objects.
     */
    public ArrayList<MenuItem> getCurrentItems() {
        return this.currentItems;
    }

    /**
     * A getter method returns the current order number for this order.
     *
     * @return the current order number
     */
    public int getCurrentOrderNumber() {
        return this.currentOrderNumber;
    }

    /**
     * A getter method returns the subtotal for the order.
     * The subtotal is the sum of the prices of all items in the order.
     *
     * @return the subtotal amount for this order.
     */
    public double getSubTotal() {
        double subTotal = 0;
        for (MenuItem items : currentItems) {
            subTotal += items.price();
        }
        return subTotal;
    }

    /**
     * A getter method returns the sales tax for the order.
     * The sales tax is a percentage of the subtotal, defined by SALES_TAX.
     *
     * @return the sales tax amount for this order
     */
    public double getSalesTax() {
        return getSubTotal() * SALES_TAX;
    }

    /**
     * A getter method returns the total amount due for the order.
     * The total includes both the subtotal and the sales tax.
     *
     * @return the total amount due for this order.
     */
    public double getTotal() {
        return getSubTotal() + getSalesTax();
    }

    /**
     * Adds a list of items to the current order.
     *
     * @param item an observable list of Items to be added.
     */
    public void addItemToCurrent(ArrayList<MenuItem> item) {
        this.currentItems.addAll(item);
    }

    /**
     * Removes a specific MenuItem from the current items list.
     *
     * @param item the MenuItem to be removed.
     */
    public void removeItem(MenuItem item) {
        this.currentItems.remove(item);
    }


}
