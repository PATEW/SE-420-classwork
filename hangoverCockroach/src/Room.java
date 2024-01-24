public class Room {
    private int length;
    private int height;
    private int baseVal; // minimum floor value (always zero in current implementation)
    public Room(Coordinate input_cor) {
        this.length = input_cor.x;
        this.height = input_cor.y;
        this.baseVal = 0;
    }

    public int getLength() { return length; }
    public int getHeight() { return height; }
    public int getArea() { return (length+1) * (height+1); }
    public int getBaseVal() { return baseVal; }
}
