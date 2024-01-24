import java.util.Set;

public class Coordinate {
    public int x;
    public int y;
    public Coordinate(int input_x, int input_y) {
        this.x = input_x;
        this.y = input_y;
    }
    public boolean isValid(int min) {
        return x >= min && y >= min;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean equivalentTo(Coordinate otherCoor) {
        return x == otherCoor.getX() && y == otherCoor.getY();
    }
    public boolean equivalentToAnyInSet(Set<Coordinate> coorSet) {
        for (Coordinate coor : coorSet) {
            if (equivalentTo(coor)) { return true; }
        }
        return false;
    }
}
