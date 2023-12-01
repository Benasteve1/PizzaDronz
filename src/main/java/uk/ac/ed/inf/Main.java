package uk.ac.ed.inf;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {

    public static void main(String[] args) {
        // Check if the correct number of arguments are provided
        if (args.length != 2) {
            System.err.println("Usage: java MainClass <YYYY-MM-DD> <URL>");
            System.exit(1);
        }

        // Parse and validate the date
        LocalDate date;
        try {
            date = LocalDate.parse(args[0]);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use YYYY-MM-DD.");
            System.exit(1);
            return;
        }

        // Validate the URL
        try {
            URL url = new URL(args[1]);
        } catch (Exception e) {
            System.err.println("Invalid URL format.");
            System.exit(1);
            return;
        }

        OrderProcessing orderProcessing = new OrderProcessing(args[1]);

        try {
            // Fetch and process orders for the given date
             orderProcessing.processOrders(args[0]);

        } catch (Exception e) {
            System.err.println("Error processing orders: " + e.getMessage());
            System.exit(1);
        }

        // Future program logic...
    }
}

