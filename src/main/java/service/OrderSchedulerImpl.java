package service;

import models.Flight;
import models.Order;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;

public class OrderSchedulerImpl implements OrderScheduler {

    List<Order> orders;
    FlightScheduler flightScheduler;

    public void setFlightScheduler(FlightScheduler c) {
        this.flightScheduler = c;
    }

    public void printSchedule() {
        HashMap<String, List<Flight>> hm = this.flightScheduler.getFlights();
        // Printing all scheduled flights
        for (List<Flight> flights: hm.values()) {
            for (Flight flight : flights) {
                for (Order order : flight.getOrders()) {
                    String s = "order: " + order.getOrderNumber() + ", ";
                    s += "flightNumber: " + flight.getFlightNumber() + ", ";
                    s += "departure: " + flight.getDeparture() + ", ";
                    s += "arrival: " + flight.getArrival() + ", ";
                    s += "day: " + flight.getDay();
                    System.out.println(s);
                }
            }
        }
        // Printing all non scheduled flights
        if (this.orders != null) {
            for (Order order: this.orders) {
                String s = "order: " + order.getOrderNumber() + ", ";
                s += "flightNumber: not scheduled";
                System.out.println(s);
            }
        }
    }

    public void scheduleOrders() {
        this.loadJson();
        this.computeOrders();
    }

    private void loadJson() {
        this.orders = new ArrayList();
        String file = "C:\\code\\java-air-tech\\src\\main\\resources\\orders.json";

        try {
            String content = new String(Files.readAllBytes(Path.of(file)));
            JSONObject orders = new JSONObject(content);

            // Extracting all order numbers to sort them
            List<String> orderNumbers = new ArrayList();
            Iterator<String> i = orders.keys();
            while(i.hasNext()) {
                String number = i.next();
                orderNumbers.add(number);
            }

            // sort list of orders to keep priority
            Collections.sort(orderNumbers);

            // Add all order objects
            for (String number: orderNumbers) {
                JSONObject orderInfo = orders.getJSONObject(number);
                String destination = orderInfo.getString("destination");
                Order newOrder = new Order(number, destination);
                this.orders.add(newOrder);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void computeOrders() {
        /* Iterate through the list of orders
         *   Get the earliest flight for the destination
         *   if flight not full -> assign order to that flight
         *   if flight is full -> get next flight to destination
         *       if no flight available set as not scheduled
         * */
        this.flightScheduler.clearFlightOrders();
        HashMap<String, List<Flight>> flights = this.flightScheduler.getFlights();
        List<Order> ordersNotScheduled = new ArrayList();

        for (Order order: this.orders) {
            // There is at least one flight for the given order
            if (flights.get(order.getDestination()) != null) {

                List<Flight> potentialFlights = flights.get(order.getDestination());
                boolean scheduled = false;

                for (Flight flight: potentialFlights) {
                    if (flight.addOrder(order)) {
                        // Adding was successful and order is scheduled
                        scheduled = true;
                        break;
                    }
                }

                if (!scheduled) {
                    ordersNotScheduled.add(order);
                }

            }
            else {
                ordersNotScheduled.add(order);
            }
        }

        this.orders = ordersNotScheduled;
    }
}
