package Structures;

/**
 * Position on the map
 */
public class Position {
    /**
     * X coordinate
     */
    public int x;

    /**
     * Y coordinate
     */
    public int y;

    /**
     * Construct a new position
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compute squared euclidean distance between two positions
     *
     * @param p1 First position
     * @param p2 Second position
     * @return Squared euclidean distance between given positions
     */
    public static int squaredDistanceBetween(Position p1, Position p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;
        return deltaX * deltaX + deltaY * deltaY;
    }
}
