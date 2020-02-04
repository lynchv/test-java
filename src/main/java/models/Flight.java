package models;

import java.util.ArrayList;
import java.util.List;

public class Flight {
    private int day;
    private int flightNumber;
    private int capacity;
    private String departure;
    private String arrival;
    private List<Order> orders = new ArrayList();

    public Flight(int day, int flightNumber, int capacity, String departure, String arrival) {
        super();
        this.day = day;
        this.flightNumber = flightNumber;
        this.capacity = capacity;
        this.departure = departure;
        this.arrival = arrival;
    }

    public int getDay() {
        return day;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public boolean addOrder(Order order) {
        if (this.orders.size() < this.capacity) {
            this.orders.add(order);
            return true;
        }
        else {
            return false;
        }
    }

    public void clearOrders() {
        this.orders = new ArrayList();
    }
}
