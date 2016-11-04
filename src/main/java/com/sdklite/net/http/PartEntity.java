package com.sdklite.net.http;

/**
 * Represents the HTTP multipart message entity
 * 
 * @author johnsonlee
 *
 */
public interface PartEntity extends HttpEntity {

    /**
     * Returns the filename
     */
    public String getFilename();

    public String getContentTransferEncoding();
    
}
