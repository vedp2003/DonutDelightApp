package com.example.rucafeapp;

import java.util.ArrayList;

/**
 * This class represents a sandwich menu item in the cafe.
 * This is a subclass of MenuItem and includes specific functionality,
 * such as bread type, protein, optional add-ons, and custom pricing
 * based on the chosen protein and add-ons.
 *
 * @author Ved Patel, Vivek Manthri
 */
public class Sandwich extends MenuItem {

    public static final String BEEF = "Beef";
    public static final String CHICKEN = "Chicken";
    public static final String FISH = "Fish";
    public static final String BAGEL_BREAD = "Bagel";
    public static final String WHEAT_BREAD = "Wheat Bread";
    public static final String SOUR_DOUGH_BREAD = "Sour Dough";
    public static final String CHEESE_ADDON = "Cheese";
    public static final String LETTUCE_ADDON = "Lettuce";
    public static final String TOMATOES_ADDON = "Tomatoes";
    public static final String ONIONS_ADDON = "Onions";
    public static final double BEEF_PRICE = 10.99;
    public static final double CHICKEN_PRICE = 8.99;
    public static final double FISH_PRICE = 9.99;
    public static final double VEGGIES_ADD_ON_PRICE = 0.30;
    public static final double CHEESE_ADD_ON_PRICE = 1.00;
    private final String bread;
    private final String protein;
    private final ArrayList<String> sandwichAddOns;

    /**
     * Parameterized constructor requires 3 parameters to create a Sandwich menu item object
     *
     * @param bread          the type of bread for the sandwich
     * @param protein        the protein choice for the sandwich
     * @param sandwichAddOns an array list of add-ons for the sandwich
     */
    public Sandwich(String bread, String protein, ArrayList<String> sandwichAddOns) {
        this.bread = bread;
        this.protein = protein;
        this.sandwichAddOns = sandwichAddOns;
    }

    /**
     * A getter method returns the list of add-ons for sandwich.
     *
     * @return an ObservableList of Strings representing the add-ons.
     */
    public ArrayList<String> getSandwichAddOns() {
        return sandwichAddOns;
    }

    /**
     * Calculates the total price of the sandwich.
     * Considers the base price of the selected protein,
     * and the additional cost for each add-on.
     *
     * @return the total price amount.
     */
    @Override
    public double price() {
        double addOnPrice = 0;
        for (String addons : this.getSandwichAddOns()) {
            if (addons.equals(CHEESE_ADDON)) {
                addOnPrice += CHEESE_ADD_ON_PRICE;
            } else {
                addOnPrice += VEGGIES_ADD_ON_PRICE;
            }
        }

        double basePrice;
        switch (this.protein) {
            case BEEF:
                basePrice = BEEF_PRICE;
                break;
            case CHICKEN:
                basePrice = CHICKEN_PRICE;
                break;
            case FISH:
                basePrice = FISH_PRICE;
                break;
            default:
                basePrice = 0.0;
        }
        if (basePrice != 0.0) {
            super.setPrice(basePrice * super.getQuantity() + addOnPrice);
            return super.getPrice();
        }
        return 0.0;
    }

    /**
     * Returns a string representation of a Sandwich menu item object.
     *
     * @return a string representation of the sandwich menu item.
     */
    @Override
    public String toString() {

        if (getSandwichAddOns().size() > 0) {
            return super.toString() + " " + protein + " Sandwich on " + bread + " " +
                    getSandwichAddOns().toString() + " " + String.format("$%.2f", price());
        }
        return super.toString() + " " + protein + " Sandwich on " + bread + " " +
                String.format("$%.2f", price());
    }
}

