package uk.ac.ed.inf;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import uk.ac.ed.inf.ilp.constant.OrderStatus;
import uk.ac.ed.inf.ilp.constant.OrderValidationCode;
import uk.ac.ed.inf.ilp.data.CreditCardInformation;
import uk.ac.ed.inf.ilp.data.Order;
import uk.ac.ed.inf.ilp.data.Restaurant;
import uk.ac.ed.inf.ilp.data.Pizza;
import uk.ac.ed.inf.ilp.interfaces.OrderValidation;
public class OrderValidator implements OrderValidation {
    private boolean isValidCardNumber(String cardNumber) {
        // Check if it's a 16-digit number
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }
    private boolean isValidCVV(String cvv) {
        // Check if CVV is a 3-digit number
        return cvv != null && cvv.matches("\\d{3}");
    }
    public boolean isValidDate(String expiryDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            YearMonth expiryDate = YearMonth.parse(expiryDateStr, formatter);
            YearMonth currentDate = YearMonth.now();

            // Check if the current date is after the expiry date
            return currentDate.isBefore(expiryDate) || currentDate.equals(expiryDate);
        } catch (DateTimeParseException e) {
            return false; // The date format was invalid
        }
    }

    public boolean isTotalPriceCorrect(Order orderToValidate) {
        int calculatedTotal = 100; //represents delivery charge
        for (Pizza pizza : orderToValidate.getPizzasInOrder()) {
            calculatedTotal += pizza.priceInPence();
        }
        return calculatedTotal == orderToValidate.getPriceTotalInPence();
    }

    private boolean isRestaurantOpenOn(Restaurant restaurant, LocalDate orderDate) {
        DayOfWeek orderDay = orderDate.getDayOfWeek();

        for (DayOfWeek day : restaurant.openingDays()) {
            if (day.equals(orderDay)) {
                return true;
            }
        }
        return false;
    }

    public Order validateOrder(Order orderToValidate, Restaurant[] definedRestaurants){
        orderToValidate.setOrderStatus(OrderStatus.VALID_BUT_NOT_DELIVERED);
        orderToValidate.setOrderValidationCode(OrderValidationCode.NO_ERROR);


        CreditCardInformation cardInfo = orderToValidate.getCreditCardInformation();
        if(!isValidCardNumber(cardInfo.getCreditCardNumber())){
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.CARD_NUMBER_INVALID);
            return orderToValidate;

        }
        if(!isValidCVV((cardInfo.getCvv()))){
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.CVV_INVALID);
            return orderToValidate;
        }
        if(!isValidDate(cardInfo.getCreditCardExpiry())){
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.EXPIRY_DATE_INVALID);
            return orderToValidate;
        }
        if (!isTotalPriceCorrect(orderToValidate)) {
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.TOTAL_INCORRECT);
            return orderToValidate;
        }
        // Validate the Pizzas
        Pizza[] pizzas = orderToValidate.getPizzasInOrder();
        // Check if there is more than 4 Pizzas
        if (pizzas.length > 4){
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.MAX_PIZZA_COUNT_EXCEEDED);
            return orderToValidate;
        }
        Set<Restaurant> restaurantsWithOrderedPizzas = new HashSet<>();
        // Iterate through each Pizza in the order
        for (Pizza orderedPizza : pizzas) {
            boolean pizzaFoundInRestaurants = false;

            // Check if Pizza is in one of the restaurants
            for (Restaurant restaurant : definedRestaurants){
                for (Pizza restPizza : restaurant.menu()){
                    if (orderedPizza.name().equals(restPizza.name())){
                        pizzaFoundInRestaurants = true;
                        restaurantsWithOrderedPizzas.add(restaurant);
                        break;
                    }
                }
                if (pizzaFoundInRestaurants){
                    break;
                }
            }
            if(!pizzaFoundInRestaurants){
                orderToValidate.setOrderStatus(OrderStatus.INVALID);
                orderToValidate.setOrderValidationCode(OrderValidationCode.PIZZA_NOT_DEFINED);
                return orderToValidate;
            }
        }
        if(restaurantsWithOrderedPizzas.size() > 1){
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.PIZZA_FROM_MULTIPLE_RESTAURANTS);
            return orderToValidate;
        }

        Restaurant restaurantDateCheck = restaurantsWithOrderedPizzas.iterator().next();
        LocalDate orderDate = orderToValidate.getOrderDate();
        if (!isRestaurantOpenOn(restaurantDateCheck, orderDate)) {
            orderToValidate.setOrderStatus(OrderStatus.INVALID);
            orderToValidate.setOrderValidationCode(OrderValidationCode.RESTAURANT_CLOSED);
            return orderToValidate;
        }
        return orderToValidate;
    }
}