package service;

import models.Flight;

import java.util.HashMap;
import java.util.List;

public interface FlightScheduler {
    void printFlights();

    HashMap<String, List<Flight>> getFlights();

    void addFlight(int day, int capacity, String departure, String arrival);

    void clearFlightOrders();

}
