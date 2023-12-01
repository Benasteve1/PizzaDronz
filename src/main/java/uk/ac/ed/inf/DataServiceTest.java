package uk.ac.ed.inf;

import java.util.List;

public class DataServiceTest {

    public static void main(String[] args) {
        DataService dataService = new DataService(); // Assuming DataService is your class for fetching data

        try {
            // Fetching and printing restaurants
            List<RestaurantRep> restaurants = dataService.fetchRestaurants();
            System.out.println("Restaurants:");
            restaurants.forEach(System.out::println);

            // Fetching and printing orders
            List<OrderRep> orders = dataService.fetchOrders("2023-11-06");
            System.out.println("\nOrders:");
            orders.forEach(System.out::println);

            // Fetching and printing central area
            CentralAreaRep centralArea = dataService.fetchCentralArea();
            System.out.println("\nCentral Area:");
            System.out.println(centralArea);

            // Fetching and printing no-fly zones
            List<NoFlyZoneRep> noFlyZones = dataService.fetchNoFlyZones();
            System.out.println("\nNo-Fly Zones:");
            noFlyZones.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

