package uk.ac.ed.inf;

public class MenuItemRep {
    private String name;
    private int priceInPence;

    // Constructor
    public MenuItemRep(String name, int priceInPence) {
        this.name = name;
        this.priceInPence = priceInPence;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceInPence() {
        return priceInPence;
    }

    public void setPriceInPence(int priceInPence) {
        this.priceInPence = priceInPence;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "MenuItemRep{" +
                "name='" + name + '\'' +
                ", priceInPence=" + priceInPence +
                '}';
    }
}