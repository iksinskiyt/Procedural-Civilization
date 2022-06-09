import Structures.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest extends Position {
    public PositionTest() {
        super(100, 100);
    }

    @Test
    public void addTest() {
        Assertions.assertEquals(0, squaredDistanceBetween(this, this));
        Assertions.assertEquals(0,
                squaredDistanceBetween(this, new Position(100, 100)));
        Assertions.assertEquals(200,
                squaredDistanceBetween(this, new Position(110, 110)));
        Assertions.assertEquals(2144465,
                squaredDistanceBetween(this, new Position(1029, 1232)));
        Assertions.assertEquals(96195805,
                squaredDistanceBetween(this, new Position(-3242, -9121)));
    }
}
