package com.sdklite.net;

/**
 * Represents an exception caused by malformed MIME type
 * 
 * @author johnsonlee
 *
 */
@SuppressWarnings("serial")
public class MalformedMimeTypeException extends RuntimeException {

    public MalformedMimeTypeException() {
        super();
    }

    public MalformedMimeTypeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MalformedMimeTypeException(final String message) {
        super(message);
    }

    public MalformedMimeTypeException(final Throwable cause) {
        super(cause);
    }

}
