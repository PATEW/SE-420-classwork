public class Item {
    private int x_loc;
    private int y_loc;
    private boolean found;

    public Item(Coordinate input_cor) {
        this.x_loc = input_cor.x;
        this.y_loc = input_cor.y;
        this.found = false;
    }
    public int get_x_loc() {
        return x_loc;
    }
    public int get_y_loc() {
        return y_loc;
    }
    public boolean isFound() {
        return found;
    }
    public void gotFound() {
        found = true;
    }
}
