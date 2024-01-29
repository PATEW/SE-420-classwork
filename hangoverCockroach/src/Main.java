import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    final static int MINIMUM_ROOM_SIZE = 2;
    final static int MINIMUM_ITEM_LOCATION = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many simulations would you like to run?");

        int numSimulations = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline

        System.out.println("Where would you like to save the output?\n(Will be saved to working directory if no path is provided)");
        String filePath = scanner.nextLine().trim();

        Room room = Entitygenerator.createRoom(scanner, MINIMUM_ROOM_SIZE);
        Item aspirin = Entitygenerator.createItem("aspirin", scanner, MINIMUM_ITEM_LOCATION);
        Item water = Entitygenerator.createItem("water", scanner, MINIMUM_ITEM_LOCATION);

        CountDownLatch cdLatch = new CountDownLatch(numSimulations);
        ConcurrentHashMap<Integer, Simulation> simulationsMap = new ConcurrentHashMap<>();
        long totalRunTime = 0;
        int totalMoves = 0;

        for (int numCurrentRun = 1; numCurrentRun <= numSimulations; numCurrentRun++) {
            Item aspirinClone = new Item(new Coordinate(aspirin.get_x_loc(), aspirin.get_y_loc()));
            Item waterClone = new Item(new Coordinate(water.get_x_loc(), water.get_y_loc()));

            Simulation simulation = new Simulation(room, aspirinClone, waterClone, numCurrentRun, cdLatch);
            simulationsMap.put(numCurrentRun, simulation);

            new Thread(simulation).start();
        }

        try {
            cdLatch.await(); // wait until all sims done
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try(PrintWriter writer = new PrintWriter(filePath)) {
            for (int i = 1; i <= numSimulations; i++) {
                Simulation sim = simulationsMap.get(i);
                totalRunTime += sim.getRunTime();
                totalMoves += sim.getMovesTaken();
                if (!sim.getAllItemsFound()) { room.itemNotFound(); }
            }

            writer.println("__________ Values __________");
            writer.println("Room Size: " + room.getLength() + ", " + room.getHeight());
            writer.println("Number of simulations: " + numSimulations);
            writer.println("Aspirin Location: " + aspirin.get_x_loc() + ", " + aspirin.get_y_loc());
            writer.println("Water Location: " + water.get_x_loc() + ", " + water.get_y_loc());
            writer.println("\n__________ Averages __________");
            writer.println("Average run time: " + (totalRunTime / numSimulations) + " nano sec.");
            writer.println("Average moves taken: " + ((double) totalMoves / numSimulations));

            if (room.getItemNotFound()) {
                writer.println("\nONE OR MORE ITEMS NOT FOUND IN ROOM");
                System.out.println("\nONE OR MORE ITEMS NOT FOUND IN ROOM");
            }

            System.out.println("\nOutput saved to: " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("\nERROR: Could not print results to entered file!");
        }

        scanner.close();
    }
}