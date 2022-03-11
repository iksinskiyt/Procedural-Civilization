import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MainTest {
    // Arguments and their respective values
    private final int[] args = {50, 17, 31, 59, 66, 17, 45, 57, 31, 59, 46, 73, 81, 56, 4, 81, 65, 60, 8, 23};
    private final long[] vals = {12586269025L, 1597L, 1346269L, 956722026041L, 27777890035288L, 1597L, 1134903170L, 365435296162L, 1346269L, 956722026041L, 1836311903L, 806515533049393L, 37889062373143906L, 225851433717L, 3L, 37889062373143906L, 17167680177565L, 1548008755920L, 21L, 28657L};

    @Test
    public void addTest() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            Main.fib(0);
        });
        Assertions.assertThrows(Exception.class, () -> {
            Main.fib(93);
        });
        for (int i = 0; i < args.length; i++)
            Assertions.assertEquals(vals[i], Main.fib(args[i]));
    }
}
