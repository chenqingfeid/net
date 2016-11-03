package com.sdklite.net;

import java.util.Set;

/**
 * Represents the URI handler
 * 
 * @author johnsonlee
 *
 */
public interface UriHandler {

    /**
     * Returns the supported schemes
     */
    public Set<String> getSupportedSchemes();

    /**
     * Determine if the specified scheme is supported
     * 
     * @param scheme
     *            URI scheme
     * @return true if the scheme is supported, otherwise, false is returned
     */
    public boolean isSchemeSupported(final String scheme);

}
