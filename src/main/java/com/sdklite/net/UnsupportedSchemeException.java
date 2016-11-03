package com.sdklite.net;

/**
 * Represents an exception caused by unsupported URI scheme
 * 
 * @author johnsonlee
 *
 */
@SuppressWarnings("serial")
public class UnsupportedSchemeException extends RuntimeException {

    /**
     * Create an instance with the specified message
     * 
     * @param message
     *            The exception message
     */
    public UnsupportedSchemeException(final String message) {
        super(message);
    }

}
