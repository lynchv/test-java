package models;

public class Order {
    private String orderNumber;
    private String destination;

    public Order(String orderNumber, String destination) {
        super();
        this.orderNumber = orderNumber;
        this.destination = destination;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDestination() {
        return destination;
    }


}
