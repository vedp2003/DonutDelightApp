package com.example.rucafeapp;

/**
 * This class represents a donut menu item in the cafe.
 * This is a subclass of MenuItem and includes specific functionality,
 * such as different flavors, quantity, and price for each donut type.
 *
 * @author Ved Patel, Vivek Manthri
 */
public class Donut extends MenuItem {

    private static final double YEAST_DONUT_PRICE = 1.79;
    private static final double CAKE_DONUT_PRICE = 1.89;
    private static final double DONUT_HOLES_PRICE = 0.39;
    private String type;
    private String flavor;

    /**
     * Parameterized constructor requires 2 parameters to create a Donut menu item object
     *
     * @param type   the type of the donut which can be "Yeast Donuts", "Cake Donuts", or "Donut Holes".
     * @param flavor the flavor of the donut as a string.
     */
    public Donut(String type, String flavor) {
        this.type = type;
        this.flavor = flavor;
    }

    /**
     * Getter method returns the type of donut
     * @return the string representing the donut type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter method returns the flavor of donut
     * @return the string representing the donut flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Calculates the total price of the donut.
     * Considers the type of the donut and quantity.
     *
     * @return the total price amount
     */
    @Override
    public double price() {
        double basePrice;
        switch (this.type) {
            case "Yeast Donuts":
                basePrice = YEAST_DONUT_PRICE;
                break;
            case "Cake Donuts":
                basePrice = CAKE_DONUT_PRICE;
                break;
            case "Donut Holes":
                basePrice = DONUT_HOLES_PRICE;
                break;
            default:
                basePrice = 0.0;
        }

        if (basePrice != 0.0) {
            super.setPrice(basePrice * super.getQuantity());
            return super.getPrice();
        }
        return 0.0;
    }

    /**
     * Returns a string representation of a Donut menu item object.
     * Shows dynamic binding (polymorphism)
     *
     * @return a string representation of the donut menu item.
     */
    @Override
    public String toString() {

        return super.toString() + " " + flavor + " " + type + " " + String.format("$%.2f", price());
    }

}
