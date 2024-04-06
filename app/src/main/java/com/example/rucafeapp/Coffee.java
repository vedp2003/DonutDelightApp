package com.example.rucafeapp;

import java.util.ArrayList;

/**
 * This class represents a coffee menu item in the cafe.
 * This is a subclass of MenuItem and includes specific functionality,
 * such as cup size, optional add-ins, and price.
 *
 * @author Ved Patel, Vivek Manthri
 */
public class Coffee extends MenuItem {

    public static final double ADD_INS_PRICE = 0.30;
    public static final double SHORT_COFFEE_PRICE = 1.99;
    public static final double TALL_COFFEE_PRICE = 2.49;
    public static final double GRANDE_COFFEE_PRICE = 2.99;
    public static final double VENTI_COFFEE_PRICE = 3.49;
    private final String coffeeCupSize;
    private final ArrayList<String> coffeeAddIns;

    /**
     * Parameterized constructor requires 2 parameters to create a Coffee menu item object
     *
     * @param cupSize       the size of the coffee cup, which comes in short, tall, grande, or venti.
     * @param coffeeAddsIns an array list of Strings representing the add-ins for the coffee.
     */
    public Coffee(String cupSize, ArrayList<String> coffeeAddsIns) {
        this.coffeeCupSize = cupSize;
        this.coffeeAddIns = coffeeAddsIns;
    }

    /**
     * A getter method returns the cup size of the coffee.
     *
     * @return a String representing the cup size of the coffee.
     */
    public String getCoffeeCupSize() {
        return coffeeCupSize;
    }

    /**
     * A getter method returns the list of add-ins for coffee.
     *
     * @return an observable list of Strings representing the add-ins.
     */
    public ArrayList<String> getCoffeeAddIns() {
        return coffeeAddIns;
    }

    /**
     * Calculates the total price of the coffee.
     * Considers the base price of the selected cup size,
     * and the additional cost for each add-in.
     *
     * @return the total price amount.
     */
    @Override
    public double price() {

        double addInPrice = this.getCoffeeAddIns().size() * ADD_INS_PRICE;

        double basePrice;
        switch (this.coffeeCupSize) {
            case "Short":
                basePrice = SHORT_COFFEE_PRICE;
                break;
            case "Tall":
                basePrice = TALL_COFFEE_PRICE;
                break;
            case "Grande":
                basePrice = GRANDE_COFFEE_PRICE;
                break;
            case "Venti":
                basePrice = VENTI_COFFEE_PRICE;
                break;
            default:
                basePrice = 0.0;
                addInPrice = 0.0;
        }

        if (basePrice != 0.0) {
            super.setPrice(basePrice * super.getQuantity() + addInPrice);
            return super.getPrice();
        }
        return 0.0;
    }

    /**
     * Returns a string representation of a Coffee menu item object.
     * Shows dynamic binding (polymorphism)
     *
     * @return a string representation of the coffee menu item.
     */
    @Override
    public String toString() {
        if (getCoffeeAddIns().size() > 0) {
            return super.toString() + " " + getCoffeeCupSize() + " Coffee "
                    + getCoffeeAddIns().toString() + " " + String.format("$%.2f", price());
        }
        return super.toString() + " " + getCoffeeCupSize() + " Coffee" + " " + String.format("$%.2f", price());
    }
}
