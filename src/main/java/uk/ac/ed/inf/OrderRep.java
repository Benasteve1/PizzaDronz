package uk.ac.ed.inf;

import java.util.List;

public class OrderRep {
    private String orderNo;
    private String orderDate;
    private String orderStatus;
    private String orderValidationCode;
    private int priceTotalInPence;
    private List<PizzaItemRep> pizzasInOrder;
    private CreditCardInfoRep creditCardInformation;

    // Getters and Setters
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderValidationCode() {
        return orderValidationCode;
    }

    public void setOrderValidationCode(String orderValidationCode) {
        this.orderValidationCode = orderValidationCode;
    }

    public int getPriceTotalInPence() {
        return priceTotalInPence;
    }

    public void setPriceTotalInPence(int priceTotalInPence) {
        this.priceTotalInPence = priceTotalInPence;
    }

    public List<PizzaItemRep> getPizzasInOrder() {
        return pizzasInOrder;
    }

    public void setPizzasInOrder(List<PizzaItemRep> pizzasInOrder) {
        this.pizzasInOrder = pizzasInOrder;
    }

    public CreditCardInfoRep getCreditCardInformation() {
        return creditCardInformation;
    }

    public void setCreditCardInformation(CreditCardInfoRep creditCardInformation) {
        this.creditCardInformation = creditCardInformation;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "OrderRep{" +
                "orderNo='" + orderNo + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderValidationCode='" + orderValidationCode + '\'' +
                ", priceTotalInPence=" + priceTotalInPence +
                ", pizzasInOrder=" + pizzasInOrder +
                ", creditCardInformation=" + creditCardInformation +
                '}';
    }
}
