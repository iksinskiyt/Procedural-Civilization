public class Position {
    public int x;
    public int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static int squaredDistanceBetween(Position p1, Position p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;
        return deltaX * deltaX + deltaY * deltaY;
    }
}
