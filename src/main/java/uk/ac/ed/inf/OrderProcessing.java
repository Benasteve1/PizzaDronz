package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.constant.OrderStatus;
import uk.ac.ed.inf.ilp.data.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessing {

    private DataService dataService;
    private OrderValidator orderValidator;
    private String resultDirectory = "resultfiles"; // Directory to store result files

    public OrderProcessing(String baseUrl) {
        this.dataService = new DataService(baseUrl);
        this.orderValidator = new OrderValidator();
    }

    // Method to process orders and generate required outputs
    public void processOrders(String date) throws Exception {
        List<RestaurantRep> restaurantReps = dataService.fetchRestaurants();
        List<OrderRep> orderReps = dataService.fetchOrders(date);

        // Convert RestaurantReps to Restaurants
        Restaurant[] restaurants = restaurantReps.stream()
                .map(rep -> new Restaurant(
                        rep.getName(),
                        new LngLat(rep.getLocation().getLng(), rep.getLocation().getLat()),
                        rep.getOpeningDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase())).toArray(DayOfWeek[]::new),
                        rep.getMenu().stream().map(item -> new Pizza(item.getName(), item.getPriceInPence())).toArray(Pizza[]::new)
                ))
                .toArray(Restaurant[]::new);

        List<Order> validatedOrders = new ArrayList<>();
        JsonArray deliveriesJsonArray = new JsonArray();
        Map<String, LngLat> validOrderRestaurantMap = new HashMap<>();

        // Process and validate each order
        for (OrderRep orderRep : orderReps) {
            Order order = new Order(
                    orderRep.getOrderNo(),
                    LocalDate.parse(orderRep.getOrderDate()),
                    orderRep.getPriceTotalInPence(),
                    orderRep.getPizzasInOrder().stream().map(pizzaRep -> new Pizza(pizzaRep.getName(), pizzaRep.getPriceInPence())).toArray(Pizza[]::new),
                    new CreditCardInformation(orderRep.getCreditCardInformation().getCreditCardNumber(), orderRep.getCreditCardInformation().getCreditCardExpiry(), orderRep.getCreditCardInformation().getCvv())
            );
            Order validatedOrder = orderValidator.validateOrder(order, restaurants);

            // Add to JSON array for deliveries file
            JsonObject orderJson = new JsonObject();
            orderJson.addProperty("orderNo", validatedOrder.getOrderNo());
            orderJson.addProperty("orderStatus", validatedOrder.getOrderStatus().toString());
            orderJson.addProperty("orderValidationCode", validatedOrder.getOrderValidationCode().toString());
            orderJson.addProperty("costInPence", validatedOrder.getPriceTotalInPence() + 100); // Adding £1 delivery charge
            deliveriesJsonArray.add(orderJson);

            // Add to list and map if the order is valid
            if (validatedOrder.getOrderStatus() == OrderStatus.VALID_BUT_NOT_DELIVERED) {
                validatedOrders.add(validatedOrder);
              //  Restaurant associatedRestaurant = findAssociatedRestaurant(validatedOrder, restaurants);
             //   validOrderRestaurantMap.put(validatedOrder.getOrderNo(), associatedRestaurant.location());
            }
        }

        // Write deliveries file
        writeJsonToFile(deliveriesJsonArray, resultDirectory + "/deliveries-" + date + ".json");

        // Future logic to use validatedOrders and validOrderRestaurantMap for flight path algorithm...
    }

    private void writeJsonToFile(JsonArray jsonArray, String filePath) {
        try {
            // Create the directories if they do not exist
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(jsonArray.toString());
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  //  private Restaurant findAssociatedRestaurant(Order order, Restaurant[] restaurants) {
        // Logic to find the restaurant based on the pizzas in the order
        // Return the found Restaurant object
   // }

}