import java.util.Scanner;

public final class Entitygenerator {
    static Room createRoom(Scanner scanner, int minRoomSize) {
        int x, y;
        System.out.println("Please input the room dimensions as 'x y' (only integers >= " + minRoomSize + " allowed):");
        while(true) {
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");

            if (tokens.length == 2 && hasOnlyNumbers(tokens[0]) && hasOnlyNumbers(tokens[1])) {
                Coordinate coordinates = new Coordinate(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                if (coordinates.isValid(minRoomSize)) {
                    return new Room(coordinates);
                }
            }
            System.out.println("\nInvalid room coordinates, please try again:");
        }
    }
    static Item createItem(String name, Scanner scanner, int minItemLocation) {
        int x, y;
        System.out.println("Please input the " + name + " coordinates as 'x y' (only integers >= " + minItemLocation + " allowed):");
        while(true) {
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");

            if (tokens.length == 2 && hasOnlyNumbers(tokens[0]) && hasOnlyNumbers(tokens[1])) {
                Coordinate coordinates = new Coordinate(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                if (coordinates.isValid(minItemLocation)) {
                    return new Item(coordinates);
                }
            }
            System.out.println("\nInvalid item coordinates, please try again:");
        }
    }
    public static boolean hasOnlyNumbers(String str) {
        String numericRegex = "^[0-9]+$";
        return str.matches(numericRegex);
    }
}
