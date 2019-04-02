package pipez.core;

public class PipezExceptionHandler {

	/**
	 * A standard general handler for all exceptions in Pipez.
	 * This is similar to how logging works in the real world e.g. log4j, sl4j 
	 * where there is a central logger for the system.
	 * 
	 * This is not how to do exception handling in a real system 
	 * where we should handle exceptions at appropriate sites.
	 * 
	 * @param ex
	 */
	public static void handle(Exception ex) {
		System.err.println();
		System.err.println(ex.getMessage());
		System.err.println();
	}
}
