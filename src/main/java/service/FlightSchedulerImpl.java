package service;

import models.Flight;

import java.util.*;

public class FlightSchedulerImpl implements FlightScheduler  {

    List<Flight> localDB = new ArrayList<Flight>();

    public FlightSchedulerImpl() {
        // This is to prepopulate the 2 days of flights given in the scenario since we don't have a real DB
        // day 1
        this.addFlight(1, 20, "YUL", "YYZ");
        this.addFlight(1, 20, "YUL", "YYC");
        this.addFlight(1, 20, "YUL", "YVR");
        // day 2
        this.addFlight(2, 20, "YUL", "YYZ");
        this.addFlight(2, 20, "YUL", "YYC");
        this.addFlight(2, 20, "YUL", "YVR");
    }

    public void printFlights() {
        for(Flight f: this.localDB) {
            String s = "Flight: " + f.getFlightNumber() + ", ";
            s += "departure: " + f.getDeparture() + ", ";
            s += "arrival: " + f.getArrival() + ", ";
            s += "day: " + f.getDay();
            System.out.println(s);
        }
        // Flight: 1, departure: YUL, arrival: YYZ, day: 1
    }

    public HashMap<String, List<Flight>> getFlights() {

        HashMap hm = new HashMap();
        for (Flight f: this.localDB) {
            List<Flight> flights;
            if (hm.get(f.getArrival()) == null) {
                flights = new ArrayList<Flight>();
            }
            else {
                flights = (List<Flight>) hm.get(f.getArrival());
            }
            flights.add(f);
            // Need to sort collection by day to conserve priority;
            // more recent flights will be filled first
            hm.put(f.getArrival(), this.sortFlights(flights));
        }

        return hm;
    }

    public void addFlight(int day, int capacity, String departure, String arrival) {
        int flightNumber = this.localDB.size() + 1;
        Flight newFlight = new Flight(day, flightNumber, capacity, departure, arrival);
        this.localDB.add(newFlight);
    }

    public void clearFlightOrders() {
        for (Flight f: this.localDB) {
            f.clearOrders();
        }
    }

    private List<Flight> sortFlights(List<Flight> flights) {

        Collections.sort(flights, (f1, f2) -> {
            return Integer.compare(f1.getDay(), f2.getDay());
        });

        return flights;
    }

}

