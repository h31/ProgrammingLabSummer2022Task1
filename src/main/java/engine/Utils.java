package engine;

final public class Utils {
    private static final double EQUIVALENCE_DELTA = 1e-13;
    public static boolean doubleEquals(double d1, double d2) {
        return Math.abs(d1 - d2) <= EQUIVALENCE_DELTA || Double.compare(d1, d2) == 0;
    }
}
