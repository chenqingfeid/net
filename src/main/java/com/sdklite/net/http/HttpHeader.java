package com.sdklite.net.http;

/**
 * Represents the HTTP header field-value pair
 * 
 * @author johnsonlee
 *
 */
public interface HttpHeader {

    /**
     * Returns the header name
     */
    public String getName();

    /**
     * Returns the header value
     */
    public String getValue();

}
