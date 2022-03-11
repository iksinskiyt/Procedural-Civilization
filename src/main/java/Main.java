public class Main {
    // Calculate an n-th Fibonacci sequence element
    public static long fib(int n) throws Exception {
        // The first element has number 1
        // The 92-nd element is the greatest that can e represented using `long` type variable
        if (n < 1 || n > 92)
            throw new Exception("n must be between 1 and 92 (inclusive)");
        long[] fibs = new long[n];
        for (int i = 0; i < n; i++) {
            if (i < 2) fibs[i] = 1;
            else fibs[i] = fibs[i - 1] + fibs[i - 2];
        }
        return fibs[n - 1];
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Give a Fibonacci series element number as an argument (1..92 inclusive)");
            return;
        }
        long f;
        try {
            f = fib(Integer.decode(args[0]));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(f);
    }
}
