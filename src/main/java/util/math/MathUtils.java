package util.math;

public class MathUtils {
	
    /**
     * Acceptable tolerance for comparisons between floats that are not
     * much larger than 1f.
     */
	public static final float EPSILON = Math.ulp(1f);
	
	/**
     * Checks the equality of two floats using ULP (units of least precision).
	 * 
	 * @param a - first float
	 * @param b - second float
	 * @return true if the distance between floats a and b is within 5 ULPs of
	 *         their average, false otherwise.
	 */
	public static boolean floatEqualsUlp(float a, float b) {
		return  Math.abs(a - b) <= 5 * Math.ulp(0.5f * (a + b));
	}
	
    /**
     * Checks the equality of two floats using adjustable ULP (units of least
     * precision).
     * 
     * @param a - first float
     * @param b - second float
     * @param numUlp - number of units of least precision to use to test equality
     * @return true if the distance between floats a and b is within
     *         <code>numUlps</code> ULPs of their average, false otherwise.
     */
	public static boolean floatEqualsUlp(float a, float b, int numUlp) {
		return  Math.abs(a - b) <= numUlp * Math.ulp(0.5f * (a + b));
	}
	
    /**
     * Absolute tolerance comparison.
     * 
     * @param a
     * @param b
     * @param epsilon
     * @return true if the absolute distance between the arguments
     *         <code>a</code> and <code>b</code> is less than or equal to
     *         epsilon, and false otherwise.
     */
	public static boolean floatEqualsAbs(float a, float b, float epsilon) {
	    return Math.abs(a - b) <= epsilon;
	}
	
    /**
     * Absolute tolerance comparison. Uses a predefined tolerance level of
     * <code>MathUtils.EPSILON</code>.
     * 
     * @param a
     * @param b
     * @return true if the absolute distance between the arguments
     *         <code>a</code> and <code>b</code> is less than or equal to
     *         <code>MathUtils.EPSILON</code>, and false otherwise.
     */
	public static boolean floatEqualsAbs(float a, float b) {
	    return Math.abs(a - b) <= EPSILON;
	}
	
    /**
     * Relative tolerance comparison.
     * 
     * The tolerance used in the comparison is automatically adjusted based on the
     * magnitudes of the compared floats.
     * 
     * @param a
     * @param b
     * @return true if the absolute distance between the arguments
     *         <code>a</code> and <code>b</code> is less than or equal to their
     *         relative tolerance, and false otherwise.
     */
	public static boolean floatEqualsRel(float a, float b) {
	    return Math.abs(a - b) <= EPSILON * (Math.abs(a) + Math.abs(b) + 1f);
	}
	
	public static void main(String args[]) {
	    printComparisonResults(0.00000004f, 0.0f, "Small value test");
	    printComparisonResults(4.000001f, 4.000002f, "Medium value test");
	    printComparisonResults(1234567.1f, 1234567.2f, "Large value test");
	}
	
	private static void printComparisonResults(float a, float b, String testName){
	    System.out.println(testName);
	    System.out.println("a=" + a + ", " + "b=" + b);
	    System.out.println("Math.abs(a - b) : " + Math.abs(a - b));
	    System.out.println("a == b : " + (a == b));
	    System.out.print("floatEqualsRel(a, b) : " + floatEqualsRel(a, b));
	    System.out.println("\ttolerance = " + (EPSILON * (Math.abs(a) + Math.abs(b) + 1f)));
	    System.out.print("floatEqualsAbs(a, b) : " + floatEqualsAbs(a, b, EPSILON));
	    System.out.println("\ttolerance = " + EPSILON);
	    System.out.println();
	}
	
}
