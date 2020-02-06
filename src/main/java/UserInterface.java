import java.util.Scanner;

import service.FlightScheduler;
import service.OrderScheduler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserInterface {

    private boolean run = true;
    private Scanner userOptions = new Scanner(System.in);

    private FlightScheduler flightScheduler;
    private OrderScheduler orderScheduler;


    public UserInterface() {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        this.flightScheduler = (FlightScheduler) context.getBean("flightScheduler");
        this.orderScheduler = (OrderScheduler) context.getBean("orderScheduler");
    }

    public void run() {
        this.printMenuOptions();
        while(this.run) {
            switch (userOptions.nextInt()) {
                case 1:
                    System.out.println("*** Show flights ***");
                    this.flightScheduler.printFlights();
                    System.out.println("********************");
                    break;
                case 2:
                    System.out.println("*** Add flight ***");
                    this.addFlight();
                    System.out.println("******************");
                    break;
                case 3:
                    System.out.println("*** Show orders ***");
                    this.orderScheduler.printSchedule();
                    System.out.println("*******************");
                    break;
                case 4:
                    System.out.println("*** Schedule orders ***");
                    this.orderScheduler.scheduleOrders();
                    System.out.println("***********************");
                    break;
                case 9:
                    System.out.println("*** GOOD BYE ***");
                    userOptions.close();
                    this.run = false;
                    break;
                default:
                    System.out.println("Invalid option, try again!");
                    this.printMenuOptions();
                    break;
            }
        }
    }

    private void addFlight() {
        System.out.println("Flight day: ");
        int day = userOptions.nextInt();

        System.out.println("Flight capacity:");
        int capacity = userOptions.nextInt();

        userOptions.nextLine();

        System.out.println("Departure:");
        String departure = userOptions.nextLine();

        System.out.println("Arrival:");
        String arrival = userOptions.nextLine();

        this.flightScheduler.addFlight(day, capacity, departure, arrival);
    }

    private void printMenuOptions( ) {
        System.out.println("Flight option:");
        System.out.println("	1 - Show flights");
        System.out.println("	2 - Add flight");
        System.out.println("Order option:");
        System.out.println("	3 - Show orders");
        System.out.println("	4 - Schedule orders");
        System.out.println("Misc option:");
        System.out.println("	9 - Close application");
    }
}
