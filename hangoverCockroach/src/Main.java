import java.util.Scanner;

public class Main {

    final static int MINIMUM_ROOM_SIZE = 2;
    final static int MINIMUM_ITEM_LOCATION = 0;

    public static void main(String[] args) {
        System.out.println("\n\nHangover Cockroach (Clay)\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many simulations would you like to run?\n");
        Room room = Entitygenerator.createRoom(scanner, MINIMUM_ROOM_SIZE);
        Item aspirin = Entitygenerator.createItem("aspirin", scanner, MINIMUM_ITEM_LOCATION);
        Item water = Entitygenerator.createItem("water", scanner, MINIMUM_ITEM_LOCATION);
        scanner.close();

        Coordinate centerRoomCoordinate = new Coordinate(Math.floorDiv(room.getLength(),2), Math.floorDiv(room.getHeight(),2));
        Cockroach roach = new Cockroach(centerRoomCoordinate);
        //System.out.println("\n" + roach.get_x_loc() + " " + roach.get_y_loc() + "\n");

        while ((!aspirin.isFound() || !water.isFound()) && !roach.hasVisitedAllSpaces()) {
            //System.out.println("\n" + roach.get_x_loc() + " " + roach.get_y_loc() + "\n");
            roach.Move(room);
            if (roach.touchingItem(aspirin)) { aspirin.gotFound(); }
            if (roach.touchingItem(water)) { water.gotFound(); }
        }

        System.out.println("\nSimulation #1");
        System.out.println("Aspirin found: " + aspirin.isFound());
        System.out.println("Water Found: " + water.isFound());
        System.out.println("All spaces visited: " + roach.hasVisitedAllSpaces());
    }

}