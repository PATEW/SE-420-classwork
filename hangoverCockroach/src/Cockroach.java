import java.util.*;

public class Cockroach {
    private int x_loc;
    private int y_loc;
    private boolean visitedAllSpaces = false;
    private TreeMap<Integer, Direction> movesMap;
    private Set<Coordinate> visitedPoints;

    public Cockroach(Coordinate roachCoordinate) {
        movesMap = new TreeMap<>();
        visitedPoints = new HashSet<Coordinate>();
        x_loc = roachCoordinate.getX();
        y_loc = roachCoordinate.getY();
    }

    public void Move(Room room) {
        // Boundary detection
        EnumSet<Direction> possibleMoves = EnumSet.allOf(Direction.class);
        if (y_loc >= room.getHeight()) {
            removeDirections(possibleMoves, Direction.UP_LEFT, Direction.UP, Direction.UP_RIGHT);
        }
        if (y_loc <= room.getBaseVal()) {
            removeDirections(possibleMoves, Direction.DOWN_LEFT, Direction.DOWN, Direction.DOWN_RIGHT);
        }
        if (x_loc >= room.getLength()) {
            removeDirections(possibleMoves, Direction.UP_RIGHT, Direction.RIGHT, Direction.DOWN_RIGHT);
        }
        if (x_loc <= room.getBaseVal()) {
            removeDirections(possibleMoves, Direction.UP_LEFT, Direction.LEFT, Direction.DOWN_LEFT);
        }

        // pick a random move from possible moves
        Direction randomDirection = getRandomElement(possibleMoves);
        movesMap.put(movesMap.isEmpty() ? 1 : movesMap.size() + 1, randomDirection); // save the chosen direction
        System.out.println(randomDirection);

        // sorry for using a switch statement, but its only for my enum so hopefully you can forgive me
        switch (randomDirection) {
            case Direction.UP:
                y_loc += 1;
                break;
            case Direction.UP_RIGHT:
                y_loc += 1;
                x_loc += 1;
                break;
            case Direction.RIGHT:
                x_loc += 1;
                break;
            case Direction.DOWN_RIGHT:
                y_loc -= 1;
                x_loc += 1;
                break;
            case Direction.DOWN:
                y_loc -= 1;
                break;
            case Direction.DOWN_LEFT:
                y_loc -= 1;
                x_loc -= 1;
                break;
            case Direction.LEFT:
                x_loc -= 1;
                break;
            case Direction.UP_LEFT:
                y_loc += 1;
                x_loc -= 1;
                break;
        }

        // save current location / check for room completion
        Coordinate currentLocation = new Coordinate(x_loc, y_loc);
        if (!currentLocation.equivalentToAnyInSet(visitedPoints)) {
            visitedPoints.add(currentLocation); }
        if (visitedPoints.size() == room.getArea()) {
            visitedAllSpaces = true; }
    }
    public int get_y_loc() {
        return y_loc;
    }
    public int get_x_loc() {
        return  x_loc;
    }
    public boolean hasVisitedAllSpaces() {
        return visitedAllSpaces;
    }
    public boolean touchingItem(Item item) {
        return ((this.x_loc == item.get_x_loc()) && (this.y_loc == item.get_y_loc()));
    }
    public void print_moves() {
        for (Map.Entry<Integer, Direction> entry : movesMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    public static void removeDirections(EnumSet<Direction> possibleMoves, Direction... directions) {
        for (Direction direction : directions) {
            possibleMoves.remove(direction);
        }
    }
    public static Direction getRandomElement(EnumSet<Direction> set) {
        List<Direction> list = new ArrayList<>(set);
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
