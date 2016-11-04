package com.sdklite.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.sdklite.net.MimeType;

/**
 * Represents the HTTP entity
 * 
 * @author johnsonlee
 *
 */
public interface HttpEntity {

    /**
     * Returns the media type
     */
    public MimeType getContentType();

    /**
     * Returns the charset
     */
    public Charset getCharset();

    /**
     * Returns the content as input stream
     */
    public InputStream getContent() throws IOException;

    /**
     * Returns the content length
     */
    public long getContentLength();

    /**
     * Write this entity into {@code out}
     * 
     * @param out
     *            The output stream
     * @throws IOException
     *             if write failed
     */
    public void writeTo(final OutputStream out) throws IOException;

}
