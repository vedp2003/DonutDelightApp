package com.example.rucafeapp;

/**
 * Abstract class of all menu items - Donuts, Coffee, Sandwiches
 *
 * @author Ved Patel, Vivek Manthri
 */
public abstract class MenuItem {
    private int quantity;
    private double price;

    /**
     * Default constructor/no-argument constructor
     */
    public MenuItem() {
    }

    /**
     * Calculates the price of the menu item.
     * This method is abstract and must be implemented by subclasses.
     *
     * @return the calculated price of the menu item as a double.
     */
    public abstract double price();

    /**
     * A getter method returns the quantity of the menu item.
     *
     * @return a String representing the quantity of the menu item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * A setter method to set the quantity of a menu item.
     *
     * @param quantity the desired quantity of this menu item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * A getter method returns the price of the menu item.
     *
     * @return the price of this menu item as a double.
     */
    public double getPrice() {
        String priceStr = String.format("%.2f", price);
        price = Double.parseDouble(priceStr);
        return price;
    }

    /**
     * A setter method to set the price of the menu item.
     *
     * @param price the price of this menu item.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of a MenuItem object.
     *
     * @return a string representation of the menu item including its quantity.
     */
    @Override
    public String toString() {
        return "(" + quantity + ")";
    }
}

