package org.jquantlib;

import org.jquantlib.lang.exceptions.LibraryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: OSGi :: remove statics
public class QL {

    private final static Logger logger = LoggerFactory.getLogger(QL.class);

    /**
     * Throws an error if the given condition is not verified
     * <p>
     * <p>
     * <b>!!! WARNING !!! WARNING !!! WARNING !!! Will Robinson !!!</b>
     * <p>
     * <p>
     * Calls to this method <b>can be eventually removed from bytecode by AspectJ</b> when a production .JAR is generated.
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a AssertionError if the condition is not met
     */
    public static void assertion(final boolean condition, final String message) {
        if (!condition) {
            final Error e = new AssertionError(message);
            logger.error(message, e);
            throw e;
        }
    }

    /**
     * Throws an error unconditionally
     * <p>
     * <p>
     * <b>!!! WARNING !!! WARNING !!! WARNING !!! Will Robinson !!!</b>
     * <p>
     * <p>
     * Calls to this method <b>can be eventually removed from bytecode by AspectJ</b> when a production .JAR is generated.
     * <p>
     * @param message is a message emitted.
     * @throws a AssertionError if the condition is not met
     */
    public static void assertion(final String message) {
        final Error e = new AssertionError(message);
        logger.error(message, e);
        throw e;
    }


    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @Note: this method should <b>never</b> be removed from bytecode by AspectJ.
     *        If you do so, you must be plenty sure of effects and risks of this decision.
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
   public static void require(final boolean condition, final String message) {
        if (!condition) {
            throw new LibraryException(message);
        }
    }

    /**
     * Throws an error if a <b>post-condition</b> is not verified
     * <p>
     * @Note: this method should <b>never</b> be removed from bytecode by AspectJ.
     *        If you do so, you must be plenty sure of effects and risks of this decision.
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void ensure(final boolean condition, final String message) {
        if (!condition) {
            throw new LibraryException(message);
        }
    }



    //=========================================================
    //
    //                   A T T E N T I O N
    //
    //            QL.fail() was removed on purpose.
    //
    //         Please throw LibraryException instead.
    //
    //=========================================================


    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final String message) {
        logger.error(message);
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final String message, final Throwable t) {
        logger.error(message, t);
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final Throwable t) {
        logger.error(t.getMessage(), t);
    }

}