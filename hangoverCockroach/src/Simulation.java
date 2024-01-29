import java.util.concurrent.CountDownLatch;

public class Simulation implements Runnable {
    private final Room room;
    private final Item aspirin;
    private final Item water;
    private final int numCurrentRun;
    private final CountDownLatch cdLatch;
    private long runTime;
    private int movesTaken;
    private boolean allItemsFound;

    public Simulation(Room room, Item aspirin, Item water, int numCurrentRun, CountDownLatch cdLatch) {
        this.room = room;
        this.aspirin = aspirin;
        this.water = water;
        this.numCurrentRun = numCurrentRun;
        this.cdLatch = cdLatch;
        allItemsFound = false;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        Coordinate centerRoomCoordinate = new Coordinate(Math.floorDiv(room.getLength(),2), Math.floorDiv(room.getHeight(),2));
        Cockroach roach = new Cockroach(centerRoomCoordinate);

        while ((!aspirin.isFound() || !water.isFound()) && !roach.hasVisitedAllSpaces()) {
            roach.Move(room);
            if (roach.touchingItem(aspirin)) { aspirin.gotFound(); }
            if (roach.touchingItem(water)) { water.gotFound(); }
        }
        if (aspirin.isFound() && water.isFound()) { allItemsFound = true; }

        runTime = System.nanoTime() - startTime;
        movesTaken = roach.getMovesMap().size();

        // must be one print line, otherwise all lines get jumbled between sims
        System.out.println("\nSimulation #" + numCurrentRun + "\nNumber of moves: " + movesTaken + "\nMoves: " + roach.getMovesString() + "\nTime: " + runTime + " nano sec.");


        cdLatch.countDown(); // sim done
    }

    public long getRunTime() {
        return runTime;
    }
    public int getMovesTaken() {
        return movesTaken;
    }
    public boolean getAllItemsFound() {
        return allItemsFound;
    }
}
