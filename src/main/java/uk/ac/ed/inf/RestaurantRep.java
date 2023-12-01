package uk.ac.ed.inf;

import java.util.List;

public class RestaurantRep {
    private String name;
    private LocationRep location;
    private List<String> openingDays;
    private List<MenuItemRep> menu;

    // Constructor
    public RestaurantRep(String name, LocationRep location, List<String> openingDays, List<MenuItemRep> menu) {
        this.name = name;
        this.location = location;
        this.openingDays = openingDays;
        this.menu = menu;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationRep getLocation() {
        return location;
    }

    public void setLocation(LocationRep location) {
        this.location = location;
    }

    public List<String> getOpeningDays() {
        return openingDays;
    }

    public void setOpeningDays(List<String> openingDays) {
        this.openingDays = openingDays;
    }

    public List<MenuItemRep> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItemRep> menu) {
        this.menu = menu;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "RestaurantRep{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", openingDays=" + openingDays +
                ", menu=" + menu +
                '}';
    }
}
