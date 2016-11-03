package com.sdklite.net;

/**
 * Represents an exception caused by malformed MIME type
 * 
 * @author johnsonlee
 *
 */
@SuppressWarnings("serial")
public class MalformedMimeTypeException extends RuntimeException {

    /**
     * Default constructor
     */
    public MalformedMimeTypeException() {
        super();
    }

    /**
     * Create an instance with message and cause
     * 
     * @param message
     *            The message
     * @param cause
     *            The cause
     */
    public MalformedMimeTypeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create an instance with message
     * 
     * @param message
     *            The message
     */
    public MalformedMimeTypeException(final String message) {
        super(message);
    }

    /**
     * Create an instance with cause
     * 
     * @param cause
     *            The cause
     */
    public MalformedMimeTypeException(final Throwable cause) {
        super(cause);
    }

}
